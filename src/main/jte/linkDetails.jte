@param com.elwin013.yala.link.LinkDto link

@template.layout(
title = "Link details",
content = @`
    <div class="row">
        <div class="col-2"></div>
        <div class="col-8">
            Your short URL is:
            <pre>${link.shortUrl()}</pre>
            Preview URL (will show target before redirecting):
            <pre>${link.shortWithPrevievUrl()}</pre>
            And real URL is:
            <pre>${link.targetUrl()}</pre>

            <p>
                Link was created at
                ${com.elwin013.yala.App.FORMATTER.format(link.createdAt())}
            </p>

            <p>
                Link was clicked <b>${link.clicks()}</b> times.
            </p>

            <div class="row">
                <div class="col-12">
                    Click here to open link view with visit stats:
                    <a href="${link.detailsUrl()}" class="button primary">Open visits!</a>
                </div>
            </div>

            <div class="bd-error">
                <p class="bg-error">DANGER ZONE</p>
                <div class="row">
                    <div class="col-12">
                        Link to remove short URL:
                        <div class="row">
                            <div class="col-9"><input type="text" value="${link.deleteUrl()}" disabled/></div>
                            <div class="col-3"><a href="${link.deleteUrl()}" class="button danger">Delete URL!</a>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
        <div class="col-2"></div>
    </div>

`,
)

