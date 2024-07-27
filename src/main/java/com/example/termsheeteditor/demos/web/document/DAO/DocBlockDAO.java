package com.example.termsheeteditor.demos.web.document.DAO;

import com.example.termsheeteditor.demos.web.document.model.*;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.StringReader;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class DocBlockDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate parameterJdbcTemplate;

    public List<BlockDTO> getAllDocBlocks() {
        return jdbcTemplate.query("SELECT id, block, type, update_time FROM document_content", new ResultSetExtractor<List<BlockDTO>>() {
            @Override
            public List<BlockDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
                return extractDataForBlocks(rs);
            }
        });
    }

    private static List<BlockDTO> extractDataForBlocks(ResultSet rs) throws SQLException {
        List<BlockDTO> records = new ArrayList<>();
        while (rs.next()) {
            BlockDTO record = new BlockDTO();
            Clob blockClob = rs.getClob("block");
            String blockHtml = StringEscapeUtils.unescapeHtml4(blockClob.getSubString(1, (int) blockClob.length()));
            record.setHtml(blockHtml);
            record.setId(rs.getInt("id"));
            record.setType(rs.getString("type"));
            record.setUpdateTime(rs.getTimestamp("update_time").toString());
            records.add(record);
        }
        return records;
    }

    public int addDocBlock(AddBlockRequest addBlock) {
        String sql = "INSERT INTO document_content(block, type) VALUES(?,?)";
        return jdbcTemplate.update(
                sql,
                new StringReader(StringEscapeUtils.escapeHtml4(addBlock.getHtml())),
                addBlock.getType());
    }

    public int updateDocBlock(UpdateBlockRequest updateBlock) {
        String sql = "UPDATE document_content SET block = ?, type= ? update_time = CURRENT_TIMESTAMP WHERE id = ?";
        return jdbcTemplate.update(
                sql,
                new StringReader(StringEscapeUtils.escapeHtml4(updateBlock.getHtml())),
                updateBlock.getType(),
                updateBlock.getId());
    }

    public HashMap getDocDefinition(String docType, String docName) {

        String sql = "SELECT doc_name, type, block_list, header, footer FROM document WHERE type =? AND doc_name =?";
        List<DocumentDTO> documentList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(DocumentDTO.class), docType, docName);
        DocumentDTO document = documentList.get(0);
        ArrayList<Integer> ids = Arrays.stream(document.getBlockList().split(",")).mapToInt(id -> Integer.valueOf(id)).collect(()-> new ArrayList<Integer>(), List::add,List::addAll);
        ids.add(document.getHeader());
        ids.add(document.getFooter());
        Map<String, Object> sqlParams = new HashMap<String, Object>();
        sqlParams.put("blockIds", ids);
        List<BlockDTO> blocks = parameterJdbcTemplate.query(
                "SELECT id, block, type, update_time FROM document_content WHERE id IN (:blockIds)", sqlParams, new ResultSetExtractor<List<BlockDTO>>() {
                    @Override
                    public List<BlockDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        return extractDataForBlocks(rs);
                    }
                });

        HashMap docMap = new HashMap();
        List<BlockDTO> bodyBlockList = new ArrayList<>();
        blocks.forEach(block -> {
            if("header".equalsIgnoreCase(block.getType())) {
                docMap.put("header", block.getHtml());
            }
            if("footer".equalsIgnoreCase(block.getType())) {
                docMap.put("footer", block.getHtml());
            }
            if("body".equalsIgnoreCase(block.getType())) {
                bodyBlockList.add(block);
            }
        });

        String bodyBlockSequence = document.getBlockList();
        List bodyBlocks = new ArrayList();
        bodyBlockList.stream().sorted(new Comparator<BlockDTO>() {
            @Override
            public int compare(BlockDTO o1, BlockDTO o2) {

                return bodyBlockSequence.indexOf(o1.getId()+"") - bodyBlockSequence.indexOf(o2.getId()+"");
            }
        }).forEach((block) -> {
            bodyBlocks.add(block.getHtml());
        });
        
        docMap.put("contents", bodyBlocks);
        return docMap;
    }

    public int conbineBlocksToDoc(ConbineDocRequest request) {
        String ids = request.getContentIds().stream().reduce("", (totalID, blockId) -> totalID += blockId + ",").toString();
        ids = ids.substring(0, ids.length() - 1);
        String insertSQL = "INSERT INTO DOCUMENT(doc_name, type, block_list, header, footer) values(?,?,?,?,?)";
        return jdbcTemplate.update(
                insertSQL,
                request.getDocName(),
                request.getType(),
                ids,
                request.getHeaderId(),
                request.getFooterId());
    }
}
