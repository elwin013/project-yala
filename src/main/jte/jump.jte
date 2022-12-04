@param com.elwin013.yala.link.LinkDto link

!{var redirectHeader = "<meta http-equiv=\"refresh\" content=\"10; url='" + link.targetUrl() + "'\" />";}

@template.layout(
title = "Go to link - Project YALA",
head = redirectHeader,
content = @`
    <div class="row">
        <div class="col-2"></div>
        <div class="col-8">
            <p>This target is</p>
            <pre>${link.targetUrl()}</pre>
            <p>You will be redirected in 10 second. If not please click below:</p>
            <a class="button primary" href="${link.targetUrl()}">Click here to go to link!</a>
        </div>
        <div class="col-2"></div>
    </div>

`,
)
