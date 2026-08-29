package com.sankalpapp.util;

public class ShareHtmlBuilder {

    private ShareHtmlBuilder() {
    }

    public static String build(
            String title,
            String description,
            String image,
            String shareUrl,
            String redirectUrl
    ) {

        return """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                
                <meta charset="UTF-8">
                
                <title>%s</title>
                
                <meta property="og:title" content="%s"/>
                <meta property="og:description" content="%s"/>
                <meta property="og:image" content="%s"/>
                <meta property="og:url" content="%s"/>
                <meta property="og:type" content="website"/>
                <meta property="og:site_name" content="Vartman Nirnay"/>
                
                <meta name="twitter:card" content="summary_large_image"/>
                <meta name="twitter:image" content="%s"/>
                
                <meta http-equiv="refresh" content="0;url=%s"/>
                
                </head>
                
                <body>
                
                Redirecting...
                
                </body>
                
                </html>
                """.formatted(
                title,
                title,
                description,
                image,
                shareUrl,
                image,
                redirectUrl
        );
    }
}
