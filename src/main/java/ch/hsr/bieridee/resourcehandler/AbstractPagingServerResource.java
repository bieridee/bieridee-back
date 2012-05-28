package ch.hsr.bieridee.resourcehandler;

import org.restlet.resource.ServerResource;

import ch.hsr.bieridee.config.Res;

/**
 * Abstract resource for rescources with paging support.
 * 
 */
public abstract class AbstractPagingServerResource extends ServerResource {

	protected static final int ITEM_COUNT_DEFAULT = 12;

	protected boolean getNeedsPaging() {
		if (getQuery().getFirstValue(Res.PAGE_PARAMETER) == null && getQuery().getFirstValue(Res.ITEMS_PER_PAGE_PARAMETER) == null) {
			return false;
		}
		return true;
	}

	protected int getPageNumberParam() {
		final String pageNumber = getQuery().getFirstValue(Res.PAGE_PARAMETER);
		if (pageNumber != null) {
			return Integer.parseInt(pageNumber);
		}
		return 0;
	}

	protected int getNumberOfItemsParam() {
		final String nOfItemsParam = getQuery().getFirstValue(Res.ITEMS_PER_PAGE_PARAMETER);
		if (nOfItemsParam != null) {
			return Integer.parseInt(nOfItemsParam);
		}
		return ITEM_COUNT_DEFAULT;
	}

}
