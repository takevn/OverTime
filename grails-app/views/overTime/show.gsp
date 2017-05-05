<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Welcome Bip</title>

    <asset:link rel="icon" href="favicon.ico" type="image/x-ico"/>

</head>
<body>
<div class="content-wrapper">
    <section class="content-header">
        <h1>
            Show list
        </h1>
    </section>
    <div class="col-sm-4">
        <g:select from="${2006..2020}" name="selectedYear" id="selectedYear" value="${year}" class="form-control"  />
    </div>
    <div class="col-sm-10" class="table-responsive">
        <table class="table table-bordered">
            <thead>
                <tr >
                    <th class="col-sm-2">Date</th>
                    <th class="col-sm-2">Year</th>
                    <th class="col-sm-2">Month</th>
                    <th class="col-sm-2">Total Overtime</th>
                    <th class="col-sm-2">Total Overtime Weekend</th>
                </tr>
            </thead>
            <tbody>
                <tr>

                </tr>
            </tbody>

        </table>
    </div>


</div>
</body>
</html>
