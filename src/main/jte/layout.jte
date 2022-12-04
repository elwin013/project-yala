@param String title
@param String head = ""
@param gg.jte.Content content

<html lang="en">
<head>
    <link rel="stylesheet" href="https://unpkg.com/chota@latest">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=PT+Serif&display=swap" rel="stylesheet">
    <title>${title} - Project Yala</title>
    $unsafe{head}
</head>
<body>
<nav class="nav">
    <div class="nav-center">
        <a class="brand" href="/">
            <h1 style="font-family: 'PT Serif', serif;">Project YALA</h1>
        </a>
    </div>
</nav>
<div class="container text-center">
    ${content}
</div>
</body>
</html>
