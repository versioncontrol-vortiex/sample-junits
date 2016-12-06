@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:payment-context.xml" })
@TransactionConfiguration(transactionManager = "paymentTransactionManager", defaultRollback = true)
@Transactional
public class UserServiceTest {
 
    @Mock
    private LoggingService mockLoggingService;
 
    @InjectMocks
    @Autowired
    private AccountService accountService;
 
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
 
    @Test
    @Transactional
    public void removeWithPermission() {
 
        EntitledUser entitledUser = new EntitledUser();
        entitledUser.setUsername("jDhoum");
        AuthToken token = new AuthToken(entitledUser, null, "paymentRelease");
        SecurityContextHolder.getContext().setAuthentication(token);
 
        Account accountToBeDeleted = accountService.createNewAccount("987989798878");
        long accountId = accountToBeDeleted.getId();
         
        doNothing().when(mockLoggingService).notifyDelete(accountId);
 
        accountService.delete(accountToBeDeleted);
 
        assertNull(accountService.get(accountId));
        verify(mockLoggingService).notifyDelete(accountId);
    }
}