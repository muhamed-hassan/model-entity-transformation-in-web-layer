package app.web.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.persistence.entities.Customer;
import app.persistence.entities.Page;
import app.web.models.BriefCustomerReadModel;
import app.web.models.CustomerCreateModel;
import app.web.models.CustomerUpdateModel;
import app.web.models.DetailedCustomerReadModel;
import app.web.models.PageModel;
import app.web.transformers.CustomerTransformer;

@RequestMapping("customers")
@RestController
public class CustomerController {
	
	@Autowired
	private CustomerTransformer customerTransformer;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Object> openBankAccount(@RequestBody CustomerCreateModel customerCreateModel) {
		
		/* 
		 * # What should be done:
		 * - validating the inputs (customerCreateModel object)
		 * x transforming the model into a persistent-entity that will be stored later in the DB
		 * - calling the domain layer to handle the storage process of the transformed customerCreateModel
		 * - return Http-Status 201-CREATED that means successful creation of a resource in the backend-side
		 * 
		 * */	
		
		Customer customer = customerTransformer.toEntity(customerCreateModel);
		
		return new ResponseEntity<Object>(HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "{nationalId}")
	public ResponseEntity<DetailedCustomerReadModel> getDetailedViewByNationalId(@PathVariable String nationalId) {
			
		/* 
		 * # What should be done:
		 * - validating the inputs (nationalId text)
		 * - calling the domain layer to load the requested data from the data storage
		 * x transforming the loaded persistent-entity into a model that will be returned later to the client-side
		 * - return Http-Status 200-OK that means the data is found in the backend-side, along with the requested data 
		 *   in the response-body
		 * 
		 * */		
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		Object[] rawRecord = {1, "Elizabeth james", "99996670580000", dateFormat.format("1990-03-01"),
				"07700900603", "elizabeth.james.2023@gmail.com", "3a High Street, Hedge End, SOUTHAMPTON, SO31 4NG",
				"34589582", 18000.55f};
		
		DetailedCustomerReadModel detailedCustomerReadModel = customerTransformer.toDetailedCustomerReadModel(rawRecord);
		
		return new ResponseEntity<DetailedCustomerReadModel>(detailedCustomerReadModel, HttpStatus.OK);
	}
		
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<PageModel<BriefCustomerReadModel>> getPageOfCustomers(@RequestParam int pageIndex) {
			
		/* 
		 * # What should be done:
		 * - calling the domain layer to load the requested data from the data storage
		 * x transforming the loaded persistent-entities into a page of models that will be returned later to the client-side
		 * - return Http-Status 200-OK that means the data is found in the backend-side, along with the requested data 
		 *   in the response-body
		 * 
		 * */	

		List<Object[]> rawData = new ArrayList<Object[]>();
		
		Object[] rawRecord1 = {"Elizabeth james", "99996670580000", "34589582", 18000.55f};
		rawData.add(rawRecord1);
		
		Object[] rawRecord2 = {"Eleanor david", "99996670580011", "24195459", 17000.55f};
		rawData.add(rawRecord2);
		
		Object[] rawRecord3 = {"Alice john", "99996670580022", "26376737", 16000.55f};
		rawData.add(rawRecord3);
		
		Object[] rawRecord4 = {"Amelia michael", "99996670580033", "28524966", 16500.55f};
		rawData.add(rawRecord4);
		
		Object[] rawRecord5 = {"Emma joseph", "99996670580044", "27004681", 17500.55f};
		rawData.add(rawRecord5);
		
		Object[] rawRecord6 = {"Emily daniel", "99996670580055", "15668641", 18500.55f};
		rawData.add(rawRecord6);

		Object[] rawRecord7 = {"Grace noah", "99996670580066", "17734246", 19500.55f};
		rawData.add(rawRecord7);
		
		Object[] rawRecord8 = {"Olivia henry", "99996670580077", "20196447", 20500.75f};
		rawData.add(rawRecord8);
		
		Object[] rawRecord9 = {"Jackson charles", "99996679980088", "62318263", 21500.75f};
		rawData.add(rawRecord9);

		Object[] rawRecord10 = {"Leo matthew", "99993370580033", "20643276", 22500.75f};
		rawData.add(rawRecord10);
		
		Page page = new Page();
		page.setData(rawData);
		page.setFirstPage(true);
		page.setLastPage(false);
		
		PageModel<BriefCustomerReadModel> pageOfCustomers = customerTransformer.toPageModel(page);
		
		return new ResponseEntity<PageModel<BriefCustomerReadModel>>(pageOfCustomers, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "{id}")
	public ResponseEntity<Object> updateBankAccount(@PathVariable int id, @RequestBody CustomerUpdateModel customerUpdateModel) {

		/* 
		 * # What should be done:
		 * - validating the inputs (customerUpdateModel object)
		 * - calling the domain layer to handle the storage process of the fields in customerUpdateModel object
		 * - return Http-Status 204-NO_CONTENT that means successful update of a resource in the backend-side
		 * 
		 * */
		
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "{id}")
	public ResponseEntity<Object> removeBankAccount(@PathVariable int id) {
		
		/* 
		 * # What should be done:
		 * - calling the domain layer to handle the removal process of the requested data
		 * - return Http-Status 204-NO_CONTENT that means successful removal of a resource in the backend-side
		 * 
		 * */
		
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}
	
}
