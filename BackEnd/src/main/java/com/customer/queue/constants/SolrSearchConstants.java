package com.customer.queue.constants;

public class SolrSearchConstants {
	public static final String solrSearchByAccountNumberCbs="Account/select?q=accountNumberCbs:";
	public static final String solrSearchByNationalId="Customer/select?q=uniqueIdValue:";
	public static final String solrSearchByMobileNum="Customer/select?q=mobileNumber:";
	public static final String solrSearchByCustomerIdCbs="Customer/select?q=customerIdCbs:";
	public static final String solrSearchFilter="&fl=customerIdCbs";

}
