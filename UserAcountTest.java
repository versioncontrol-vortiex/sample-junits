@RunWith(MockitoJUnitRunner.class)
public class UserAccountTest {
 
    @Mock
    private AlertsService alertsService;
 
    @Mock
    private AccountDetailsDAO accountDetailsDAO;
 
    @InjectMocks
    private AccountDetailsServiceImpl accountDetails = new AccountDetailsServiceImpl();
 
    @Test
    public void createAnAccount() {
 
        String accountName = "Jakarta IPC";
        Account accountToSave = new Account(accountName);
        long accountId = 12345;
        Account databaseAccount = new Account(accountName);
        databaseAccount.setId(accountId);
 
        when(accountDetailsDAO.save(any(Account.class))).thenReturn(databaseAccount);
        doNothing().when(alertsService).notifyOfCreateAccount(accountId);
 
        Account createAccount = accountDetails.createAnAccount(accountName);
 
        assertNotNull(createAccount);
        assertEquals(accountId, createAccount.getId());
        assertEquals(accountName, createAccount.getName());
 
        verify(alertsService).notifyOfcreateAccount(accountId);
        verify(accountDetailsDAO).save(accountToSave);
    }
}