<p align="center">
 <h1 align="center">Smoothy</h1>
 <h4 align="center">An EWB which means Easy Web Builder :D</h4>
 <p align="center">At this point, Smoothy is a web-api-based Spring Boot program that intends to launch an engine for generating spring-boot-cored applications. Applications such as e-commerce websites, so that users can through an easy-to-use GUI, generate their websites with just a few clicks and forms.</p>
</p>

<p align="center">
    <a href="https://github.com/TheXSolutions/smoothy/releases/tag/v0.1.1-DEV">
        <img src="https://badgify.thex.solutions/api/badge/link?title=smoothy%20v0.1.1-DEV&icon=github&size=s&bg=orange&theme=edge" />
    </a>
    <a href="https://github.com/TheXSolutions/smoothy/search?l=java">
        <img src="https://badgify.thex.solutions/api/badge/link?title=18&icon=java&size=s&bg=pink&theme=edge" />
    </a>
    <a href="https://github.com/TheXSolutions/smoothy/blob/main/LICENSE">
        <img src="https://badgify.thex.solutions/api/badge/link?title=GPL-2.0&icon=scale-balanced&size=s&bg=cyan&theme=edge" />
    </a>
    <a href="https://discordapp.com/users/shuoros#5896">
        <img src="https://badgify.thex.solutions/api/badge/link?title=Contact%20Me!&icon=discord&size=s&bg=564e91&theme=edge" />
    </a>
</p>
<p align="center">
    <a href="https://commerce.coinbase.com/checkout/6e1472f5-3481-4949-84cf-a915e0cb8d0c">
        <img src="https://badgify.thex.solutions/api/badge/link?title=Buy%20me%20a%20%20cup%20of%20coffee%20:D&icon=hand-holding-dollar&size=s&bg=green" />
    </a>
</p>

## You want to test?

- Clone the Smoothy's repository
- Switch to the latest feature branch
- Run the following commands:

```bash
~$ mvn clean install -DskipTests -Dcheckstyle.skip
~$ mvn spring-boot:run
```

- Open your browser and go to `http://localhost:7777/`
- Generate a new website with the following yaml or json file:
```yaml
name: MyWebsite
description: A wonderful application!
applicationType: spring-boot
application:
  javaVersion: 18
  springVersion: 2.6.6
  port: 7070          
```

Or

```json
{
    "name": "MyWebsite",
    "description": "A wonderful application!",
    "applicationType": "spring-boot",
    "application": {
        "javaVersion": "18",
        "springVersion": "2.6.6",
        "port": "7070"
    }
}
```

- Or you can POST your json or yaml file to `http://localhost:7777/generate`
- Finally, download your generated website as a zip file.

**Right now the Smoothy is under construction! There is no guarantee that your generated website work properly!**

If you have any ideas or suggestions, or you want to contribute to this project, please contact me =))<3