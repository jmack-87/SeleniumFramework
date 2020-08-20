"# SeleniumFramework"

a selenium grid is required

**General Selenium Grid structure (Windows PC):**
- Selenium standalone server jar
- a batch file pointing to two other batch files, one for launching nodes, one for launching hub(s)
- nodes defined in JSON file
- hubs defined in json file
- WebDriver Server binaries per target browser

```
[root]
  selenium-server-standalone-3.14.0.jar
  fullStart.bat
  [drivers]
    geckodriver.exe
    chromedriver.exe
    IEDriverServer.exe
    MicrosoftWebDriver.exe
  [hubs]
    hubStart.bat
    hubConfig.json
  [nodes]
    nodeStart.bat
    nodeConfig.json
```

**Examples:**

fullStart.bat:
```
start .\hubs\hubStart.bat
start .\nodes\nodeStart.bat
```
nodeStart.bat:
```
java -Dwebdriver.chrome.driver=".\drivers\chromedriver.exe" -Dwebdriver.gecko.driver=".\drivers\geckodriver.exe" -Dwebdriver.ie.driver=".\drivers\IEDriverServer_64.exe" -Dwebdriver.edge.driver=".\drivers\MicrosoftWebDriver.exe" -jar selenium-server-standalone-3.14.0.jar -role node -nodeConfig .\nodes\nodeConfig.json -debug
```
hubStart.bat:
```
java -jar selenium-server-standalone-3.14.0.jar -role hub -hubConfig .\hubs\HUBConfig.json -debug
```
nodeConfig.json:
```json
{
	"capabilities": [{
		"acceptSslCerts": true,
		"browserName": "chrome",
		"cleanSession": true,
		"javascriptEnabled": true,
		"maxInstances": 3,
		"platform": "WIN10",
		"seleniumProtocol": "WebDriver"
	},
	{
		"acceptSslCerts": true,
		"browserName": "firefox",
		"cleanSession": true,
		"javascriptEnabled": true,
		"maxInstances": 3,
		"platform": "WIN10",
		"seleniumProtocol": "WebDriver"
	},
	{
		"acceptSslCerts": true,
		"browserName": "internet explorer",
		"cleanSession": true,
		"javascriptEnabled": true,
		"maxInstances": 1,
		"platform": "WIN10",
		"seleniumProtocol": "WebDriver"
	},
	{
		"acceptSslCerts": true,
		"browserName": "MicrosoftEdge",
		"cleanSession": true,
		"javascriptEnabled": true,
		"maxInstances": 1,
		"platform": "WIN10",
		"seleniumProtocol": "WebDriver"
	}],
	"custom": {},
	"debug": true,
	"downPollingLimit": 2,
	"hub": "http://localhost:4444",
	"maxSession": 5,
	"nodePolling": 5000,
	"nodeStatusCheckTimeout": 5000,
	"port": 5556,
	"proxy": "org.openqa.grid.selenium.proxy.DefaultRemoteProxy",
	"register": true,
	"registerCycle": 5000,
	"role": "node",
	"servlets": [],
	"unregisterIfStillDownAfter": 60000,
	"withoutServlets": []
}
```
hubConfig.json:
```json
{
	"port": 4444,
	"newSessionWaitTimeout": -1,
	"servlets": [],
	"withoutServlets": [],
	"custom": {},
	"capabilityMatcher": "org.openqa.grid.internal.utils.DefaultCapabilityMatcher",
	"registry": "org.openqa.grid.internal.DefaultGridRegistry",
	"throwOnCapabilityNotPresent": true,
	"cleanUpCycle": 5000,
	"role": "hub",
	"debug": true,
	"browserTimeout": 0,
	"timeout": 1800
}
```

[Selenium Server](https://www.seleniumhq.org/download/)

[Geckodriver](https://github.com/mozilla/geckodriver)

[Chromedriver](http://chromedriver.chromium.org/)

[IEDriverServer](https://github.com/SeleniumHQ/selenium/wiki/InternetExplorerDriver)

[MicrosoftWebDriver](https://developer.microsoft.com/en-us/microsoft-edge/tools/webdriver/)

[Information on Safari driver](https://developer.apple.com/documentation/webkit/testing_with_webdriver_in_safari)

**Note on Edge:**
Microsoft has plans to deprecate original Edge support sometime in the future, in favor of chromium-based Edge. To automate with original Edge, current Windows 10 users (builds > 17134) will need to run:
```DISM.exe /Online /Add-Capability /CapabilityName:Microsoft.WebDriver~~~~0.0.1.0``` from an elevated command prompt to install a build-version-appropriate binary.
The binary *MicrosoftWebDriver.exe* normally installs to the C:\Windows\System32\ directory. User of Selenium Grid may wish to copy the binary to a more manageable location like the *drivers* folder mentioned above.

If the binary is not found under C:\Windows\System32, running **where** should point the way.
Example:
```
C:\WINDOWS\system32>where MicrosoftWebDriver.exe
C:\Windows\System32\MicrosoftWebDriver.exe
```

