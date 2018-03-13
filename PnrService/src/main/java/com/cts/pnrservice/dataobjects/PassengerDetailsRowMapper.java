package com.cts.pnrservice.dataobjects;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cts.pnrservice.model.PassengerDetails;

public class PassengerDetailsRowMapper implements RowMapper<PassengerDetails>{

	@Override
	public PassengerDetails mapRow(ResultSet row, int rownum) throws SQLException {
		PassengerDetails passengerDtls = new PassengerDetails();
		passengerDtls.setPNRNumber(row.getString("PNRNumber"));
		passengerDtls.setFirstName(row.getString("FirstName"));
		passengerDtls.setLastName(row.getString("LastName"));
		passengerDtls.setPhoneNumber(row.getString("PhoneNumber"));
		
		return passengerDtls;
	}

}
