@template.layout(
title = "Index",
content = @`
    <div class="row">
        <div class="col-2"></div>
        <div class="col-8">
            <p>YALA stands for Yet Another Link-shortener App and is a simple web link shortener.</p>
            <p>You can see code of this app on the GitHub - <a href="https://github.com/elwin013/project-yala">https://github.com/elwin013/project-yala</a>
            </p>
        </div>
        <div class="col-2"></div>
    </div>

    <h2>Paste the URL to be shortened</h2>
    <form action="/create_link" method="post">
        <div class="row">
            <div class="col-2"></div>
            <div class="col-6">
                <input type="text" name="targetUrl" id="targetUrl"/>
            </div>
            <div class="col-2">
                <button type="submit" class="button primary">Shorten it!</button>
            </div>
            <div class="col-2"></div>
        </div>
    </form>
`,
)
