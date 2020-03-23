package pt.ulisboa.tecnico.learnjava.sibs.sibs;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.After;
import org.junit.Test;

import pt.ulisboa.tecnico.learnjava.bank.domain.Bank;
import pt.ulisboa.tecnico.learnjava.bank.exceptions.AccountException;
import pt.ulisboa.tecnico.learnjava.bank.exceptions.BankException;
import pt.ulisboa.tecnico.learnjava.bank.exceptions.ClientException;
import pt.ulisboa.tecnico.learnjava.bank.services.Services;
import pt.ulisboa.tecnico.learnjava.sibs.domain.Operation;
import pt.ulisboa.tecnico.learnjava.sibs.domain.Sibs;
import pt.ulisboa.tecnico.learnjava.sibs.exceptions.OperationException;
import pt.ulisboa.tecnico.learnjava.sibs.exceptions.SibsException;

public class TransferMethodTestMockito {

	Services services = mock(Services.class);

	// Test 4
	@Test
	public void success() throws BankException, AccountException, SibsException, OperationException, ClientException {
		Sibs sibs = new Sibs(100, this.services);
		String sourceIban = "CGKCK1";
		String targetIban = "CGKK1";

		sibs.transfer(sourceIban, targetIban, 100);

		assertEquals(1, sibs.getNumberOfOperations());
		assertEquals(100, sibs.getTotalValueOfOperations());
		assertEquals(100, sibs.getTotalValueOfOperationsForType(Operation.OPERATION_TRANSFER));
		assertEquals(0, sibs.getTotalValueOfOperationsForType(Operation.OPERATION_PAYMENT));
	}

	// Test5
	@Test
	public void successBeweenAccountsEqualBank()
			throws BankException, AccountException, SibsException, OperationException, ClientException {
		Sibs sibs = new Sibs(100, this.services);
		String sourceIban = "CGKCK1";
		String targetIban = "CGKCK2";

		int commission = sibs.transfer(sourceIban, targetIban, 100);

		assertEquals(100, sibs.getTotalValueOfOperations());
		assertEquals(0, commission);

	}

	@Test
	public void sucessFeeBetweenBanksDiffierent() throws SibsException, AccountException, OperationException {
		Sibs sibs = new Sibs(100, this.services);
		String sourceIban = "CGKCK1";
		String targetIban = "BESCK1";

		int commission = sibs.transfer(sourceIban, targetIban, 100);

		assertEquals(100, sibs.getTotalValueOfOperations());
		assertEquals(5, commission);

	}

	@After
	public void tearDown() {
		Bank.clearBanks();
	}

}
