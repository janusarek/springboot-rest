package hello;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import static java.util.stream.Collectors.toList;
import hello.soap.countries.Country;
import hello.soap.countries.Currency;
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
		spain.setCurrency(Currency.EUR);
		spain.setPopulation(46704314);

		countries.put(spain.getName(), spain);

		Country poland = new Country();
		poland.setName("Poland");
		poland.setCapital("Warsaw");
		poland.setCurrency(Currency.PLN);
		poland.setPopulation(38186860);

		countries.put(poland.getName(), poland);

		Country uk = new Country();
		uk.setName("United Kingdom");
		uk.setCapital("London");
		uk.setCurrency(Currency.GBP);
		uk.setPopulation(63705000);

		countries.put(uk.getName(), uk);

		Country portugal = new Country();
		portugal.setName("Portugal");
		portugal.setCapital("Lisboa");
		portugal.setCurrency(Currency.EUR);
		portugal.setPopulation(10379573);

		countries.put(portugal.getName(), portugal);

		Country germany = new Country();
		germany.setName("Germany");
		germany.setCapital("Berlin");
		germany.setCurrency(Currency.EUR);
		germany.setPopulation(82800000);

		countries.put(germany.getName(), germany);

		Country montenegro = new Country();
		montenegro.setName("Montenegro");
		montenegro.setCapital("Podgorica");
		montenegro.setCurrency(Currency.EUR);
		montenegro.setPopulation(642550);

		countries.put(montenegro.getName(), montenegro);
	}

	public Country findCountry(String name) {
		Assert.notNull(name, "The country's name must not be null");
		return countries.get(name);
	}

	public List<Country> getAllCountries() {
		return new ArrayList<Country>(countries.values());
	}

	public List<Country> getCountriesByCurrency(Currency currency) {
		return countries
				.values()
				.stream()
				.filter(c -> c.getCurrency() == currency)
				.collect(toList());
	}

	public List<Country> getCountries(Currency currency, BigInteger minPopulation) {
		Assert.notNull(currency, "The currency parameter is required");

		Stream<Country> countriesFiltered = countries
				.values()
				.stream()
				.filter(c -> c.getCurrency() == currency);

		if (minPopulation != null) {
			countriesFiltered = countriesFiltered.filter(c -> BigInteger.valueOf(c.getPopulation()).compareTo(minPopulation) >= 0 );
		}

		return countriesFiltered.collect(toList());
	}


}
