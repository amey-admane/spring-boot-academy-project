package com.acrnome.academy_amey.repository;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.acrnome.academy_amey.entity.MasterTools;

@Repository
public class MasterToolsRepository {
	private static final String GET_TOOL_MASTER_UNION_TOOL_MASTER_REFRENCE_QUERY_BY_ID = "SELECT * from public.toolsmain INNER  JOIN public.toolsrefrence USING (itemid) WHERE itemid = ? ORDER BY (itemid) ASC ";

	private static final String INSET_TOOLS_MASTER_QUERY_UPDATE = "INSERT INTO public.toolsmain (itemid, description, globaldescription, searcha, searchb, supplier) VALUES (?, ?, ?, ?, ?, ?) ON CONFLICT (itemid) DO UPDATE SET description = ? , globaldescription = ? , searcha = ? , searchb = ? , supplier = ? ";
	private static final String INSET_TOOLS_MASTER_REFRENCE_QUERY_UPDATE = "INSERT INTO PUBLIC.toolsrefrence ( itemid, location, itemgroup, orderingcode) VALUES (?,?,?,?) ON CONFLICT (itemid) DO UPDATE SET  location = ?, itemgroup = ?, orderingcode = ? ";
	private static final String DELETE_MASTER_TOOLS_DATA_QURY = "Delete from public.toolsmain where  itemid  in (:ids);";
	private static final String DELETE_MASTER_REFRENCE_TOOLS_DATA_QURY = "Delete from public.toolsrefrence where  itemid  in (:ids);";

	private static final String EDIT_TOOLS_MASTER_QUERY_UPDATE = "UPDATE public.toolsmain  SET description = ? , globaldescription = ? , searcha = ? , searchb = ? , supplier = ? where itemid = ? ;";
	private static final String EDIT_TOOLS_MASTER_REFRENCE_QUERY_UPDATE = "UPDATE  PUBLIC.toolsrefrence  SET  location = ?, itemgroup = ?, orderingcode = ? where itemid = ? ; ";

	private static String GET_COLUMNS_BY_SEARCH = "SELECT * FROM public.toolsmain INNER  JOIN public.toolsrefrence USING (itemid) WHERE ";
	private static String GET_TOOL_MASTER_UNION_TOOL_MASTER_REFRENCE_QUERY_LIMIT = "SELECT * from public.toolsmain INNER  JOIN public.toolsrefrence USING (itemid) ORDER BY ";
	private static String GET_TOOL_MASTER_UNION_TOOL_MASTER_REFRENCE_QUERY = "SELECT * from public.toolsmain INNER  JOIN public.toolsrefrence USING (itemid) ORDER BY (itemid):: Integer  ";

	private static String GET_COUNT_BY_SEARCH = "SELECT COUNT(*) FROM public.toolsmain INNER  JOIN public.toolsrefrence USING (itemid) WHERE ";
	private static final String GET_TOOL_MASTER_UNION_TOOL_MASTER_REFRENCE_LENGTH_QUERY = "SELECT COUNT(*) from public.toolsmain INNER  JOIN public.toolsrefrence USING (itemid)  ";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	public List<MasterTools> uploadExcelDataForTools(List<MasterTools> toolsDataList) {

		for (MasterTools toolsRefrenceFile : toolsDataList) {
			jdbcTemplate.update(INSET_TOOLS_MASTER_QUERY_UPDATE, toolsRefrenceFile.getItemId(),
					toolsRefrenceFile.getDescription(), toolsRefrenceFile.getGlobalDescription(),
					toolsRefrenceFile.getSearchA(), toolsRefrenceFile.getSearchB(), toolsRefrenceFile.getSupplier(),
					toolsRefrenceFile.getDescription(), toolsRefrenceFile.getGlobalDescription(),
					toolsRefrenceFile.getSearchA(), toolsRefrenceFile.getSearchB(), toolsRefrenceFile.getSupplier());

			jdbcTemplate.update(INSET_TOOLS_MASTER_REFRENCE_QUERY_UPDATE, toolsRefrenceFile.getItemId(),
					toolsRefrenceFile.getLocation(), toolsRefrenceFile.getGroup(), toolsRefrenceFile.getOrderingCode(),
					toolsRefrenceFile.getLocation(), toolsRefrenceFile.getGroup(), toolsRefrenceFile.getOrderingCode());

		}
		return toolsDataList;
	}

	public List<MasterTools> getToolsMasterData(int offSet, int limit, String orderBy, String columnToSort) {
		if (limit != 0) {
			String orderingCodeToInsert = ("itemid".equals(columnToSort)) ? "(itemid):: Integer " : columnToSort;
			return jdbcTemplate.query(
					GET_TOOL_MASTER_UNION_TOOL_MASTER_REFRENCE_QUERY_LIMIT + orderingCodeToInsert + " " + orderBy
							+ " OFFSET ? LIMIT ?",
					(rs, rowNum) -> new MasterTools(rs.getString("itemid"), rs.getString("description"),
							rs.getString("globaldescription"), rs.getString("searcha"), rs.getString("searchb"),
							rs.getString("supplier"), rs.getString("location"), rs.getString("itemgroup"),
							rs.getString("orderingcode")),
					Integer.valueOf(offSet), Integer.valueOf(limit));
		}
		return jdbcTemplate.query(GET_TOOL_MASTER_UNION_TOOL_MASTER_REFRENCE_QUERY + orderBy,
				(rs, rowNum) -> new MasterTools(rs.getString("itemid"), rs.getString("description"),
						rs.getString("globaldescription"), rs.getString("searcha"), rs.getString("searchb"),
						rs.getString("supplier"), rs.getString("location"), rs.getString("itemgroup"),
						rs.getString("orderingcode")));

	}

	public List<MasterTools> getToolsMasterDataById(String itemID) {

		return jdbcTemplate.query(GET_TOOL_MASTER_UNION_TOOL_MASTER_REFRENCE_QUERY_BY_ID,
				(rs, rowNum) -> new MasterTools(rs.getString("itemid"), rs.getString("description"),
						rs.getString("globaldescription"), rs.getString("searcha"), rs.getString("searchb"),
						rs.getString("supplier"), rs.getString("location"), rs.getString("itemgroup"),
						rs.getString("orderingcode")),
				itemID);

	}

	public int deleteToolsMasterData(List<String> itemIdDeleted) {
		Map<String, List<String>> paramForQuery = new HashMap<>();
		paramForQuery.put("ids", itemIdDeleted);
		int resulta = namedJdbcTemplate.update(DELETE_MASTER_REFRENCE_TOOLS_DATA_QURY, paramForQuery);
		int resultb = namedJdbcTemplate.update(DELETE_MASTER_TOOLS_DATA_QURY, paramForQuery);

		return (resulta + resultb) / 2;

	}

	public void addToolsMasterData(MasterTools toolsRefrenceFile) {

		jdbcTemplate.update(INSET_TOOLS_MASTER_QUERY_UPDATE, toolsRefrenceFile.getItemId(),
				toolsRefrenceFile.getDescription(), toolsRefrenceFile.getGlobalDescription(),
				toolsRefrenceFile.getSearchA(), toolsRefrenceFile.getSearchB(), toolsRefrenceFile.getSupplier(),
				toolsRefrenceFile.getDescription(), toolsRefrenceFile.getGlobalDescription(),
				toolsRefrenceFile.getSearchA(), toolsRefrenceFile.getSearchB(), toolsRefrenceFile.getSupplier());

		jdbcTemplate.update(INSET_TOOLS_MASTER_REFRENCE_QUERY_UPDATE, toolsRefrenceFile.getItemId(),
				toolsRefrenceFile.getLocation(), toolsRefrenceFile.getGroup(), toolsRefrenceFile.getOrderingCode(),
				 toolsRefrenceFile.getLocation(),toolsRefrenceFile.getGroup(), toolsRefrenceFile.getOrderingCode());

	}

	public void editToolsMasterData(MasterTools toolsRefrenceFile) {
		jdbcTemplate.update(EDIT_TOOLS_MASTER_QUERY_UPDATE, toolsRefrenceFile.getDescription(),
				toolsRefrenceFile.getGlobalDescription(), toolsRefrenceFile.getSearchA(),
				toolsRefrenceFile.getSearchB(), toolsRefrenceFile.getSupplier(), toolsRefrenceFile.getItemId());

		jdbcTemplate.update(EDIT_TOOLS_MASTER_REFRENCE_QUERY_UPDATE, toolsRefrenceFile.getLocation(),
				toolsRefrenceFile.getGroup(), toolsRefrenceFile.getOrderingCode(),
				toolsRefrenceFile.getItemId());

	}

	public List<MasterTools> searchedData(List<String> columnsToSearch, String searchTerm, int offset, int limit,
			String orderby, String columnToSort) {

		String orderingCodeToInsert = ("itemid".equals(columnToSort)) ? "(itemid):: Integer " : columnToSort;

		String queryFinal = GET_COLUMNS_BY_SEARCH;
		int i = 0;
		for (i = 0; i < columnsToSearch.size() - 1; i++) {
			queryFinal += columnsToSearch.get(i) + " LIKE '%" + searchTerm + "%' OR ";
		}
		queryFinal += columnsToSearch.get(i) + " LIKE '%" + searchTerm + "%'  ORDER BY " + orderingCodeToInsert + " "
				+ orderby + " OFFSET " + offset + " LIMIT " + limit + " ";

		System.out.print("Entered for search " + queryFinal);
		return jdbcTemplate.query(queryFinal,
				(rs, rowNum) -> new MasterTools(rs.getString("itemid"), rs.getString("description"),
						rs.getString("globaldescription"), rs.getString("searcha"), rs.getString("searchb"),
						rs.getString("supplier"), rs.getString("location"), rs.getString("itemgroup"),
						rs.getString("orderingcode")));
	}

	public int getSearchedDataLength(List<String> columnsToSearch, String searchTerm) {
		String queryFinal = GET_COUNT_BY_SEARCH;
		int i = 0;
		for (i = 0; i < columnsToSearch.size() - 1; i++) {
			queryFinal += columnsToSearch.get(i) + " LIKE '%" + searchTerm + "%' OR ";
		}
		queryFinal += columnsToSearch.get(i) + " LIKE '%" + searchTerm + "%'  ";

		return jdbcTemplate.queryForObject(queryFinal, Integer.class);

	}

	public int getStoredDataLength() {
		return jdbcTemplate.queryForObject(GET_TOOL_MASTER_UNION_TOOL_MASTER_REFRENCE_LENGTH_QUERY, Integer.class);
	}
}
