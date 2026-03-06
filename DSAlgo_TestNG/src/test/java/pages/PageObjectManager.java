package pages;

public class PageObjectManager {

	private LaunchPage launchPage;
	private LoginPage loginPage;
	private RegisterPage registerPage;
	private LinkedListPage linkedlistPage;
	private StackPage stackPage;
	private HomePage homePage;
	private DataStructurePage dataStructurePage;
	private GraphPage graphPage;
	private ArrayPage arraypage;
	private QueuePage queuepage;
	private TreePage treepage;
	private TryEditorPage tryeditorPage;

	public LaunchPage getLaunchPage() {
		if (launchPage == null) {
			launchPage = new LaunchPage();
		}
		return launchPage;
	}

	public LoginPage getLoginPage() {
		if (loginPage == null) {
			loginPage = new LoginPage();
		}
		return loginPage;
	}

	public RegisterPage getRegisterPage() {
		if (registerPage == null) {
			registerPage = new RegisterPage();
		}
		return registerPage;
	}

	public LinkedListPage getLinkedListPage() {
		if (linkedlistPage == null) {
			linkedlistPage = new LinkedListPage();
		}
		return linkedlistPage;
	}

	public StackPage getStackPage() {
		if (stackPage == null) {
			stackPage = new StackPage();
		}
		return stackPage;
	}

	public HomePage getHomePage() {
		if (homePage == null) {
			homePage = new HomePage();
		}
		return homePage;
	}

	public DataStructurePage getDataStructurePage() {
		if (dataStructurePage == null) {
			dataStructurePage = new DataStructurePage();
		}
		return dataStructurePage;
	}

	public GraphPage getGraphPage() {
		if (graphPage == null) {
			graphPage = new GraphPage();
		}
		return graphPage;
	}

	public ArrayPage getArrayPage() {
		if (arraypage == null) {
			arraypage = new ArrayPage();
		}
		return arraypage;
	}
	public QueuePage getQueuePage() {
		if (queuepage == null) {
			queuepage = new QueuePage();
		}
		return queuepage;
	}
	
	public TreePage getTreePage() {
		if (treepage == null) {
			treepage = new TreePage();
		}
		return treepage;
	}
	public TryEditorPage getTryEditorPage() {
		if (tryeditorPage == null) {
			tryeditorPage = new TryEditorPage();
		}
		return tryeditorPage;
	}
}
