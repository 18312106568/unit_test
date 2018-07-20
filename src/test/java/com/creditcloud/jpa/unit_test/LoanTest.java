package com.creditcloud.jpa.unit_test;

import com.creditcloud.jpa.unit_test.constant.QQConstant;
import com.creditcloud.jpa.unit_test.model.PtuiCheckVK;
import com.creditcloud.jpa.unit_test.repository.QQLoginRepository;
import com.creditcloud.jpa.unit_test.service.LoginService;
import com.creditcloud.jpa.unit_test.utils.LoginUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class LoanTest extends UnitTestApplicationTests {
    
    @Autowired
    QQLoginRepository loginRepository;
    
    @Autowired
    LoginService loginService;
    
    @Test
    public void testQQLogin(){
        System.out.println(String.format(QQConstant.QQ_LOGIN_URL, "3250537630","123abc123abc"));
        System.out.println(loginRepository.findQQLoginByUin("3250537630"));
    }
    
    @Test
    public void testPtuiCheckVK(){
        String qq = "3602158526";
        String loginSig =  LoginUtil.getLoginSig();
        PtuiCheckVK vk = loginService.isSafeLogin(qq,loginSig);
        System.out.println(vk);
        loginService.tryLogin(qq, loginSig, vk);
    }
    
    
    
/*
    @PersistenceContext
    EntityManager em;
    

    @Test
    public void nativeQueryLoan() {
        StringBuilder sql = new StringBuilder("SELECT " +
                "  a.ID as id," +
                "  a.CAP_REQUEST_NO as capRequestNo," +
                "  c.NAME as productName," +
                "  a.AMOUNT as amount," +
                "  (IFNULL(b.INSTALLMENTSERVERFEE, 0) + IFNULL(b.AMOUNTINTEREST, 0) -" +
                "   IFNULL(b.couponAomunt, 0)) AS couponAmount," +
                "  a.TIMERECORDED as timeRecord," +
                "  a.PAY_DATE as payDate," +
                "  a.REPAY_DATE as repayDate," +
                "  a.STATUS as loanStatus" +
                " FROM TB_LOAN a " +
                "  INNER JOIN TB_LOAN_REPAYMENT b ON a.ID = b.LOAN_ID " +
                "  INNER JOIN TB_LOANREQUEST_PRODUCT c ON a.PRODUCT_ID = c.ID where 1=1 ");
        Query q = em.createNativeQuery(sql.toString());
        List<Object[]> resultList = q.getResultList();
        resultList.forEach(e -> {
            System.out.println(Arrays.toString(e));
        });
    }

    @Test
    public void testBorrowHistory() {
        String sql = "SELECT\n" +
                "\ta.`TITLE` as loanTitle,\n" +
                "\ta.`USER_ID` as userId,\n" +
                "\ta.`AMOUNT` as amount,\n" +
                "\t ifnull(b.`REPAYAMOUNT`, 0) as repayAmount,\n" +
                "\td.`LOAN_RATE` as loanRate,\n" +
                "\td.`PRODUCTKEY` as loanProductType,\n" +
                "\td.`LOAN_DURATION_DAYS` as loanDurationDays,\n" +
                "\ta.`REPAY_DATE` as repayDate,\n" +
                "\t(select count(1) from TB_REPAYMENT_OVERDUE_RECORD f where f.REPAYMENT_ID = b.ID) as dueDays,\n" +
                "\ta.`STATUS` as status\n" +
                "FROM\n" +
                "\t`TB_LOAN` a\n" +
                "\tINNER JOIN `TB_LOAN_REPAYMENT` b on a.id =b.LOAN_ID\n" +
                "\tLEFT JOIN `TB_LOANREQUEST_PRODUCT` d ON a.`PRODUCT_ID` = d.id \n" +
                "WHERE\n" +
                "\ta.`USER_ID` IN (?1,?2)";
        Query q = em.createNativeQuery(sql).setParameter(1, "E5A9B920-78D3-4556-A068-FA9A87CF4426")
                .setParameter(2,"E5A9B920-78D3-4556-A068-FA9A87CF4426");
        List<Object[]> resultList = q.getResultList();
        resultList.forEach(e -> {
            System.out.println(Arrays.toString(e));
        });
    }
    
    @Test
    public void testQuoteClient(){
        try {
            QuoteClient quoteClient = new QuoteClient();
//            GetQuoteResponse response = quoteClient.getQuote("HISTORY_DECL_DOWNLOAD_ACCREDIT_DATA");
          //  System.err.println(response.getGetQuoteResult());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
*/
}
