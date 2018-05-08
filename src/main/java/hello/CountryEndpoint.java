package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import hello.soap.countries.GetCountryRequest;
import hello.soap.countries.GetCountryResponse;
import hello.soap.countries.GetAllCountriesRequest;
import hello.soap.countries.GetAllCountriesResponse;
import hello.soap.countries.GetCountriesRequest;
import hello.soap.countries.GetCountriesResponse;

@Endpoint
public class CountryEndpoint {
	private static final String NAMESPACE_URI = "http://hello/SOAP/countries";

	private CountryRepository countryRepository;

	@Autowired
	public CountryEndpoint(CountryRepository countryRepository) {
		this.countryRepository = countryRepository;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
	@ResponsePayload
	public GetCountryResponse getCountry(@RequestPayload GetCountryRequest request) {
		GetCountryResponse response = new GetCountryResponse();
		response.setCountry(countryRepository.findCountry(request.getName()));

		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllCountriesRequest")
	@ResponsePayload
	public GetAllCountriesResponse getCountry(@RequestPayload GetAllCountriesRequest request) {
		GetAllCountriesResponse response = new GetAllCountriesResponse();
		response.setCountries(countryRepository.getAllCountries());

		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountriesRequest")
	@ResponsePayload
	public GetCountriesResponse getCountries(@RequestPayload GetCountriesRequest request) {
		GetCountriesResponse response = new GetCountriesResponse();
		response.setCountries(countryRepository.getCountries(request.getCurrency(), request.getMinPopulation()));

		return response;
	}
}
