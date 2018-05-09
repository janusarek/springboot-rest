package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
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

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountriesRequest")
	@ResponsePayload
	public GetCountriesResponse getCountries(@RequestPayload GetCountriesRequest request) {
		GetCountriesResponse response = new GetCountriesResponse();
		response.setCountries(countryRepository.getCountries(request.getCurrency(), request.getMinPopulation(), request.getServerID()));

		return response;
	}
}
