package com.ibm.demo.service;

import com.ibm.demo.model.Transaction;
import com.ibm.demo.model.TransactionType;
import com.ibm.demo.repository.TransactionsRepository;
import org.assertj.core.api.BDDAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

    @Mock
    private TransactionsRepository transactionsRepository;

    @InjectMocks
    private TransactionService service;

    @Test
    public void findTransactions_with_no_filters() {

        Transaction tx1 = new Transaction();
        tx1.setTransactionTime(LocalDateTime.now().minusDays(7));
        tx1.setType(TransactionType.WITHDRAW);
        tx1.setAmount(BigDecimal.TEN);
        tx1.setAccountNumber("1234");

        Transaction tx2 = new Transaction();
        tx2.setTransactionTime(LocalDateTime.now().minusDays(7));
        tx2.setType(TransactionType.WITHDRAW);
        tx2.setAmount(BigDecimal.TEN);
        tx2.setAccountNumber("1234");

        List<Transaction> expected = Arrays.asList(tx1, tx2);

        Pageable pageable = PageRequest.of(0, 20);
        Page<Transaction> page = new PageImpl<>(expected);

        given(transactionsRepository.findByAccountNumberAndType("1234", null, null, null, pageable)).willReturn(page);
        Page<Transaction> actual = service.findTransactions("1234", null, null, 0, 20);

        BDDAssertions.then(actual.getContent().size()).isEqualTo(expected.size());
        BDDAssertions.then(actual.getContent().get(0).getAccountNumber()).isEqualTo("1234");
        BDDAssertions.then(actual.getContent().get(1).getAmount()).isEqualTo(BigDecimal.TEN);
    }

    @Test
    public void findTransactions_with_withdrawal_type_and_today() {

        Transaction tx1 = new Transaction();
        tx1.setTransactionTime(LocalDateTime.now().minusDays(7));
        tx1.setType(TransactionType.WITHDRAW);
        tx1.setAmount(BigDecimal.TEN);
        tx1.setAccountNumber("1234");

        Transaction tx2 = new Transaction();
        tx2.setTransactionTime(LocalDateTime.now().minusDays(7));
        tx2.setType(TransactionType.WITHDRAW);
        tx2.setAmount(BigDecimal.TEN);
        tx2.setAccountNumber("1234");

        List<Transaction> expected = Arrays.asList(tx1, tx2);

        Pageable pageable = PageRequest.of(0, 20);
        Page<Transaction> page = new PageImpl<>(expected);

        given(transactionsRepository.findByAccountNumberAndType(eq("1234"), eq(TransactionType.WITHDRAW.toString()),
                eq(LocalDate.now().atStartOfDay()), any(), eq(pageable))).willReturn(page);
        Page<Transaction> actual = service.findTransactions("1234", TransactionType.WITHDRAW.toString(), "Today", 0, 20);

        BDDAssertions.then(actual.getContent().size()).isEqualTo(expected.size());
        BDDAssertions.then(actual.getContent().get(0).getAccountNumber()).isEqualTo("1234");
        BDDAssertions.then(actual.getContent().get(1).getAmount()).isEqualTo(BigDecimal.TEN);
    }

    @Test
    public void findTransactions_with_deposit_type_and_last7days() {

        Transaction tx1 = new Transaction();
        tx1.setTransactionTime(LocalDateTime.now().minusDays(7));
        tx1.setType(TransactionType.DEPOSIT);
        tx1.setAmount(BigDecimal.TEN);
        tx1.setAccountNumber("1234");

        Transaction tx2 = new Transaction();
        tx2.setTransactionTime(LocalDateTime.now().minusDays(7));
        tx2.setType(TransactionType.DEPOSIT);
        tx2.setAmount(BigDecimal.TEN);
        tx2.setAccountNumber("1234");

        List<Transaction> expected = Arrays.asList(tx1, tx2);

        Pageable pageable = PageRequest.of(0, 20);
        Page<Transaction> page = new PageImpl<>(expected);

        given(transactionsRepository.findByAccountNumberAndType(eq("1234"), eq(TransactionType.DEPOSIT.toString()),
                eq(LocalDate.now().minusDays(7).atStartOfDay()), any(), eq(pageable))).willReturn(page);
        Page<Transaction> actual = service.findTransactions("1234", TransactionType.DEPOSIT.toString(), "Last 7 Days", 0, 20);

        BDDAssertions.then(actual.getContent().size()).isEqualTo(expected.size());
        BDDAssertions.then(actual.getContent().get(0).getAccountNumber()).isEqualTo("1234");
        BDDAssertions.then(actual.getContent().get(1).getAmount()).isEqualTo(BigDecimal.TEN);
        BDDAssertions.then(actual.getContent().get(1).getType()).isEqualTo(TransactionType.DEPOSIT);
    }

    @Test
    public void findTransactions_with_withdrawal_type_and_lastmonth() {

        Transaction tx1 = new Transaction();
        tx1.setTransactionTime(LocalDateTime.now().minusDays(7));
        tx1.setType(TransactionType.WITHDRAW);
        tx1.setAmount(BigDecimal.TEN);
        tx1.setAccountNumber("1234");

        Transaction tx2 = new Transaction();
        tx2.setTransactionTime(LocalDateTime.now().minusDays(7));
        tx2.setType(TransactionType.WITHDRAW);
        tx2.setAmount(BigDecimal.TEN);
        tx2.setAccountNumber("1234");

        List<Transaction> expected = Arrays.asList(tx1, tx2);

        Pageable pageable = PageRequest.of(0, 20);
        Page<Transaction> page = new PageImpl<>(expected);

        given(transactionsRepository.findByAccountNumberAndType(eq("1234"), eq(TransactionType.WITHDRAW.toString()),
                eq(LocalDate.now().minusMonths(1).atStartOfDay()), any(), eq(pageable))).willReturn(page);
        Page<Transaction> actual = service.findTransactions("1234", TransactionType.WITHDRAW.toString(), "Last Month", 0, 20);

        BDDAssertions.then(actual.getContent().size()).isEqualTo(expected.size());
        BDDAssertions.then(actual.getContent().get(0).getAccountNumber()).isEqualTo("1234");
        BDDAssertions.then(actual.getContent().get(1).getAmount()).isEqualTo(BigDecimal.TEN);
    }

    @Test
    public void findTransactions_with_withdrawal_type_and_given_timerange() {

        Transaction tx1 = new Transaction();
        tx1.setTransactionTime(LocalDateTime.now().minusDays(7));
        tx1.setType(TransactionType.WITHDRAW);
        tx1.setAmount(BigDecimal.TEN);
        tx1.setAccountNumber("1234");

        Transaction tx2 = new Transaction();
        tx2.setTransactionTime(LocalDateTime.now().minusDays(7));
        tx2.setType(TransactionType.WITHDRAW);
        tx2.setAmount(BigDecimal.TEN);
        tx2.setAccountNumber("1234");

        List<Transaction> expected = Arrays.asList(tx1, tx2);

        Pageable pageable = PageRequest.of(0, 20);
        Page<Transaction> page = new PageImpl<>(expected);

        given(transactionsRepository.findByAccountNumberAndType("1234", TransactionType.WITHDRAW.toString(),
                LocalDate.parse("2021-01-01").atStartOfDay(), LocalDate.parse("2021-01-24").atStartOfDay(), pageable)).willReturn(page);
        Page<Transaction> actual = service.findTransactions("1234", TransactionType.WITHDRAW.toString(), "2021-01-01~2021-01-24", 0, 20);

        BDDAssertions.then(actual.getContent().size()).isEqualTo(expected.size());
        BDDAssertions.then(actual.getContent().get(0).getAccountNumber()).isEqualTo("1234");
        BDDAssertions.then(actual.getContent().get(1).getAmount()).isEqualTo(BigDecimal.TEN);
        BDDAssertions.then(actual.getContent().get(1).getType()).isEqualTo(TransactionType.WITHDRAW);
    }
}
