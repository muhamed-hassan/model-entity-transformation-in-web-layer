package app.web.transformers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import app.persistence.entities.BankAccountInfo;
import app.persistence.entities.ContactInfo;
import app.persistence.entities.Customer;
import app.persistence.entities.Page;
import app.web.models.BriefCustomerReadModel;
import app.web.models.CustomerCreateModel;
import app.web.models.DetailedCustomerReadModel;
import app.web.models.PageModel;

@Component
public class CustomerTransformer {
	
	@Value("${date.pattern}")
    private String datePattern;
	
	public Customer toEntity(CustomerCreateModel customerCreateModel) {
		
		Customer customer = new Customer();
		customer.setName(customerCreateModel.getName());
		customer.setNationalId(customerCreateModel.getNationalId());
		try {			
			DateFormat dateFormat = new SimpleDateFormat(datePattern);
			customer.setDateOfBirth(dateFormat.parse(customerCreateModel.getDateOfBirth()));			
		} catch (ParseException e) {}
		
		ContactInfo contactInfo = new ContactInfo();
		contactInfo.setCellPhone(customerCreateModel.getCellPhone());		
		contactInfo.setEmail(customerCreateModel.getEmail());
		contactInfo.setMailingAddress(customerCreateModel.getMailingAddress());
		customer.setContactInfo(contactInfo);
		
		BankAccountInfo bankAccountInfo = new BankAccountInfo();		
		customer.setBankAccountInfo(bankAccountInfo);
		
		return customer;
	}
	
	public DetailedCustomerReadModel toDetailedCustomerReadModel(Object[] rawRecord) {
		
		DateFormat dateFormat = new SimpleDateFormat(datePattern);
		
		DetailedCustomerReadModel detailedCustomerReadModel = new DetailedCustomerReadModel();
		detailedCustomerReadModel.setId((int) rawRecord[0]);
		detailedCustomerReadModel.setName((String) rawRecord[1]);		
		detailedCustomerReadModel.setNationalId((String) rawRecord[2]);
		detailedCustomerReadModel.setDateOfBirth(dateFormat.format((Date) rawRecord[3]));	
		detailedCustomerReadModel.setCellPhone((String) rawRecord[4]);
		detailedCustomerReadModel.setEmail((String) rawRecord[5]);
		detailedCustomerReadModel.setMailingAddress((String) rawRecord[6]);		
		detailedCustomerReadModel.setAccountNumber((String) rawRecord[7]);
		detailedCustomerReadModel.setBalance((float) rawRecord[8]);
		
		return detailedCustomerReadModel;
	}
	
	public PageModel<BriefCustomerReadModel> toPageModel(Page page) {
		
		List<Object[]> rawData = page.getData();
		HashSet<BriefCustomerReadModel> briefCustomerReadModels = new HashSet<BriefCustomerReadModel>();
		for (int cursor = 0; cursor < rawData.size(); cursor++) {
			
			Object[] currentRawRecord = rawData.get(cursor);
						
			BriefCustomerReadModel briefCustomerReadModel = new BriefCustomerReadModel();
			briefCustomerReadModel.setName((String) currentRawRecord[0]);
			briefCustomerReadModel.setNationalId((String) currentRawRecord[1]);
			briefCustomerReadModel.setAccountNumber((String) currentRawRecord[2]);
			briefCustomerReadModel.setBalance((float) currentRawRecord[3]);
			
			briefCustomerReadModels.add(briefCustomerReadModel);
		}
		
		PageModel<BriefCustomerReadModel> pageOfCustomers = new PageModel<BriefCustomerReadModel>();
		pageOfCustomers.setData(briefCustomerReadModels);
		pageOfCustomers.setFirstPage(page.isFirstPage());
		pageOfCustomers.setLastPage(page.isLastPage());
		
		return pageOfCustomers;
	}

}
