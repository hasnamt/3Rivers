package com.ibm.demo.controller;

import com.ibm.demo.AggregateServiceApplication;
import com.ibm.demo.model.Transaction;
import com.ibm.demo.model.TransactionType;
import com.ibm.demo.service.TransactionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = {AggregateServiceApplication.class})
public class TransactionControllerTest {


    @MockBean
    private TransactionService service;
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;


    @Before
    public void setUp() {
        this.mvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getTransactions_with_no_filters() throws Exception {

        Transaction tx1 = new Transaction();
        tx1.setTransactionTime(LocalDateTime.now().minusDays(7));
        tx1.setType(TransactionType.WITHDRAW);
        tx1.setAmount(BigDecimal.TEN);
        tx1.setAccountNumber("1234");

        Transaction tx2 = new Transaction();
        tx2.setTransactionTime(LocalDateTime.now().minusDays(7));
        tx2.setType(TransactionType.DEPOSIT);
        tx2.setAmount(BigDecimal.valueOf(1212));
        tx2.setAccountNumber("1234");

        List<Transaction> expected = Arrays.asList(tx1, tx2);

        Page<Transaction> page = new PageImpl<>(expected);

        given(service.findTransactions("1234",
                null, null, 0, 20)).willReturn(page);

        mvc.perform(get("/v1/accounts/1234/transactions"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.transactions[0].accountNumber", is("1234")))
                .andExpect(jsonPath("$.transactions[0].type", is(TransactionType.WITHDRAW.name())))
                .andExpect(jsonPath("$.transactions[0].amount", is(10)))
                .andExpect(jsonPath("$.transactions[1].type", is(TransactionType.DEPOSIT.name())))
                .andExpect(jsonPath("$.transactions[1].amount", is(1212)));

    }

    @Test
    public void get_todays_transactions_with_deposit_type() throws Exception {

        Transaction tx1 = new Transaction();
        tx1.setTransactionTime(LocalDateTime.now().minusDays(7));
        tx1.setType(TransactionType.DEPOSIT);
        tx1.setAmount(BigDecimal.TEN);
        tx1.setAccountNumber("1234");

        Transaction tx2 = new Transaction();
        tx2.setTransactionTime(LocalDateTime.now().minusDays(7));
        tx2.setType(TransactionType.DEPOSIT);
        tx2.setAmount(BigDecimal.valueOf(1212));
        tx2.setAccountNumber("1234");

        List<Transaction> expected = Arrays.asList(tx1, tx2);

        Page<Transaction> page = new PageImpl<>(expected);

        given(service.findTransactions("1234",
                "DEPOSIT", "Today", 0, 20)).willReturn(page);

        mvc.perform(get("/v1/accounts/1234/transactions?type=DEPOSIT&time_range=Today"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.transactions[0].accountNumber", is("1234")))
                .andExpect(jsonPath("$.transactions[0].type", is(TransactionType.DEPOSIT.name())))
                .andExpect(jsonPath("$.transactions[0].amount", is(10)))
                .andExpect(jsonPath("$.transactions[1].type", is(TransactionType.DEPOSIT.name())))
                .andExpect(jsonPath("$.transactions[1].amount", is(1212)));

    }

    @Test
    public void get_last7days_transactions_with_deposit_type() throws Exception {

        Transaction tx1 = new Transaction();
        tx1.setTransactionTime(LocalDateTime.now().minusDays(7));
        tx1.setType(TransactionType.DEPOSIT);
        tx1.setAmount(BigDecimal.TEN);
        tx1.setAccountNumber("1234");

        Transaction tx2 = new Transaction();
        tx2.setTransactionTime(LocalDateTime.now().minusDays(7));
        tx2.setType(TransactionType.DEPOSIT);
        tx2.setAmount(BigDecimal.valueOf(1212));
        tx2.setAccountNumber("1234");

        List<Transaction> expected = Arrays.asList(tx1, tx2);

        Page<Transaction> page = new PageImpl<>(expected);

        given(service.findTransactions("1234",
                "DEPOSIT", "Last 7 Days", 0, 20)).willReturn(page);

        mvc.perform(get("/v1/accounts/1234/transactions?type=DEPOSIT&time_range=Last 7 Days"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.transactions[0].accountNumber", is("1234")))
                .andExpect(jsonPath("$.transactions[0].type", is(TransactionType.DEPOSIT.name())))
                .andExpect(jsonPath("$.transactions[0].amount", is(10)))
                .andExpect(jsonPath("$.transactions[1].type", is(TransactionType.DEPOSIT.name())))
                .andExpect(jsonPath("$.transactions[1].amount", is(1212)));

    }

    @Test
    public void get_transactions_with_deposit_type_for_a_timerange() throws Exception {

        Transaction tx1 = new Transaction();
        tx1.setTransactionTime(LocalDateTime.now().minusDays(7));
        tx1.setType(TransactionType.DEPOSIT);
        tx1.setAmount(BigDecimal.TEN);
        tx1.setAccountNumber("1234");

        Transaction tx2 = new Transaction();
        tx2.setTransactionTime(LocalDateTime.now().minusDays(7));
        tx2.setType(TransactionType.DEPOSIT);
        tx2.setAmount(BigDecimal.valueOf(1212));
        tx2.setAccountNumber("1234");

        List<Transaction> expected = Arrays.asList(tx1, tx2);

        Page<Transaction> page = new PageImpl<>(expected);

        given(service.findTransactions("1234",
                "DEPOSIT", "2021-01-01~2021-01-24", 0, 20)).willReturn(page);

        mvc.perform(get("/v1/accounts/1234/transactions?type=DEPOSIT&time_range=2021-01-01~2021-01-24"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.transactions[0].accountNumber", is("1234")))
                .andExpect(jsonPath("$.transactions[0].type", is(TransactionType.DEPOSIT.name())))
                .andExpect(jsonPath("$.transactions[0].amount", is(10)))
                .andExpect(jsonPath("$.transactions[1].type", is(TransactionType.DEPOSIT.name())))
                .andExpect(jsonPath("$.transactions[1].amount", is(1212)));

    }
}
