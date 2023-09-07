package com.poscodx.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.poscodx.mysite.vo.GuestbookVo;

public class GuestbookDao {

	public List<GuestbookVo> findAll() {
		List<GuestbookVo> result = new ArrayList<GuestbookVo>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			String sql =
					"select no, name, contents, reg_date "
					+ "from guestbook "
					+ "order by reg_date desc";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int no = rs.getInt(1);
				String name = rs.getString(2);
				String contents = rs.getString(3);
				String regDate = rs.getString(4);
				
				GuestbookVo vo = new GuestbookVo();
				vo.setNo(no);
				vo.setName(name);
				vo.setContents(contents);
				vo.setRegDate(regDate);
				
				result.add(vo);
			}
			
		} catch (SQLException e) {
			System.out.println("Guestbook Select error: " + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}

	public String findPasswordByNo(int no) {
		String result = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			String sql =
					"select password "
					+ "from guestbook "
					+ "where no = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, no);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String password = rs.getString(1);
				
				result = password;
			}
			
		} catch (SQLException e) {
			System.out.println("Guestbook Select error: " + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public void insert(GuestbookVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			String sql =
					"insert into guestbook(name, password, contents, reg_date) "
					+ "values(?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");			
			String regDate = sdf.format(new Date());
			vo.setRegDate(regDate);

			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getContents());
			pstmt.setString(4, vo.getRegDate());
			
			rs = pstmt.executeQuery();
			
		} catch (SQLException e) {
			System.out.println("Guestbook Insert error: " + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	public void deleteByNo(String no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			String sql =
					"delete from guestbook "
					+ "where no = ?";
			pstmt = conn.prepareStatement(sql);
				
			pstmt.setString(1, no);
			
			rs = pstmt.executeQuery();
			
		} catch (SQLException e) {
			System.out.println("Guestbook Delete error: " + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private Connection getConnection() throws SQLException {
		Connection conn = null;
			
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			String url = "jdbc:mariadb://192.168.0.175:3307/webdb?charset=utf8";
			conn = DriverManager.getConnection(url, "webdb", "webdb123");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패 : " + e);
		} 

		return conn;
	}
	
}
