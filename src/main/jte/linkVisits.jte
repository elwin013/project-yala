@param com.elwin013.yala.link.LinkDto link
@param com.elwin013.yala.link.visit.LinkVisitsDto visits


@template.layout(
title = "Link details",
content = @`
    <div class="row">
        <div class="col-2"></div>
        <div class="col-8">
            Your short URL is:
            <pre>${link.shortUrl()}</pre>
            <p>
                Link was created at
                ${com.elwin013.yala.App.FORMATTER.format(link.createdAt())}
            </p>

            <p>
                Link was clicked <b>${link.clicks()}</b> times.
            </p>

            <table>
                <thead>
                <tr>
                    <td>Day and hour</td>
                    <td>Visits</td>
                </tr>
                </thead>
                <tbody>
                @for(var line : visits.getVisits())
                    <tr>
                        <td>${com.elwin013.yala.App.FORMATTER.format(line.getId())}</td>
                        <td>${line.getCount()}</td>
                    </tr>
                @endfor
                </tbody>
            </table>
        </div>
        <div class="col-2"></div>
    </div>

`,
)

