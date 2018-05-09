package hello;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import static java.util.stream.Collectors.toList;
import hello.soap.countries.Country;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import java.math.BigInteger;
import java.util.stream.Stream;

@Component
public class CountryRepository {
	private static final Map<String, Country> countries = new HashMap<>();

	@PostConstruct
	public void initData() {
		Country spain = new Country();
		spain.setName("Spain");
		spain.setCapital("Madrid");
		spain.setCurrency("EUR");
		spain.setPopulation(46704314);
		spain.setServerID(9223372036854775807L);

		countries.put(spain.getName(), spain);

		Country poland = new Country();
		poland.setName("Poland");
		poland.setCapital("Warsaw");
		poland.setCurrency("PLN");
		poland.setPopulation(38186860);
		poland.setServerID(9223372036854775806L);

		countries.put(poland.getName(), poland);

		Country uk = new Country();
		uk.setName("United Kingdom");
		uk.setCapital("London");
		uk.setCurrency("GBP");
		uk.setPopulation(63705000);
		uk.setServerID(9223372036854775805L);

		countries.put(uk.getName(), uk);

		Country portugal = new Country();
		portugal.setName("Portugal");
		portugal.setCapital("Lisboa");
		portugal.setCurrency("EUR");
		portugal.setPopulation(10379573);
		portugal.setServerID(9223372036854775807L);

		countries.put(portugal.getName(), portugal);

		Country germany = new Country();
		germany.setName("Germany");
		germany.setCapital("Berlin");
		germany.setCurrency("EUR");
		germany.setPopulation(82800000);
		germany.setServerID(9223372036854775806L);

		countries.put(germany.getName(), germany);

		Country montenegro = new Country();
		montenegro.setName("Montenegro");
		montenegro.setCapital("Podgorica");
		montenegro.setCurrency("EUR");
		montenegro.setPopulation(642550);
		montenegro.setServerID(9223372036854775804L);

		countries.put(montenegro.getName(), montenegro);
	}

	public Country findCountry(String name) {
		Assert.notNull(name, "The country's name must not be null");
		return countries.get(name);
	}

	public List<Country> getAllCountries() {
		return new ArrayList<Country>(countries.values());
	}

	public List<Country> getCountriesByCurrency(String currency) {
		return countries
				.values()
				.stream()
				.filter(c -> c.getCurrency() == currency)
				.collect(toList());
	}

	public List<Country> getCountries(String currency, BigInteger minPopulation, List<Long> serverIds) {
		Assert.notNull(currency, "The currency parameter is required");

		Stream<Country> countriesFiltered = countries
				.values()
				.stream()
				.filter(c -> c.getCurrency().equals(currency));

		if (minPopulation != null) {
			countriesFiltered = countriesFiltered.filter(c -> BigInteger.valueOf(c.getPopulation()).compareTo(minPopulation) >= 0 );
		}

		if (serverIds.size() > 0) {
			countriesFiltered = countriesFiltered.filter(c -> serverIds.contains(c.getServerID()));
		}

		return countriesFiltered.collect(toList());
	}


}
