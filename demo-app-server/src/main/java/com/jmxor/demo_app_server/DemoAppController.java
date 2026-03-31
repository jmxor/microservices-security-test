package com.jmxor.demo_app_server;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoAppController {

    @GetMapping("/")
    public String home() {
        return """
        <html>
        <body>
        <h1>App Server</h1>
        <button onclick="callApi()">Call API</button>
        <pre id='output'></pre>
        <script>
        function callApi() {
            fetch('https://api.local.test/api/hello', {
                credentials: 'include'
            })
            .then(res => res.json())
            .then(data => {
                document.getElementById('output').innerText = JSON.stringify(data);
            });
        }
        </script>
        </body>
        </html>
        """;
    }
}
