package ch.hsr.bieridee.models;

import java.util.List;

import ch.hsr.bieridee.utils.DBUtil;

/**
 * ADD ANOTHER LAYER OF INDIRECTION!!!1!!
 *
 */
public final class BrandModel {
	private BrandModel(){
		// do not instantiate
	}
	public static List<String> getAll() {
		return DBUtil.getAllBrands();
	}
}
