package com.cts.pnrservice.dataobjects;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cts.pnrservice.model.PnrDetails;
import com.cts.pnrservice.utils.DateUtils;

public class PnrDetailsRowMapper implements RowMapper<PnrDetails>{

	@Override
	public PnrDetails mapRow(ResultSet row, int rownum) throws SQLException {
		PnrDetails pnrDtls = new PnrDetails();
		pnrDtls.setPNRNumber(row.getString("PNRNumber"));
		pnrDtls.setArrivalCode(row.getString("ArrivalCode"));
		pnrDtls.setArrivalPoint(row.getString("ArrivalPoint"));
		pnrDtls.setClassOfService(row.getString("ClassOfService"));
		try {
			pnrDtls.setDate(DateUtils.getDateStringMMDDYYYY(row.getDate("Date")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pnrDtls.setDeparturePoint(row.getString("DeparturePoint"));
		pnrDtls.setFlightNumber(row.getString("FlightNumber"));
		
	    return pnrDtls;
	}
}
