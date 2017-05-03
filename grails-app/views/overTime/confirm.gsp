<!doctype html>
<html>
<head>
    <meta name="layout" content=""/>


    <asset:link rel="icon" href="favicon.ico" type="image/x-ico"/>
    <style>
    @media print {
    *   {
        margin-left: 10px !important;
        margin-right: 10px !important;
        margin-top:50px;
        margin-bot:50px;
        padding: 0 !important;
        }
        th,td.padding-5 {
        padding-left:15px !important;
        padding-right:15px !important;
        }


    #controls, .footer, .footerarea{ display: none; }
    html, body {
    /*changing width to 100% causes huge overflow and wrap*/
    height:100%;
    overflow: hidden;
    background: #FFF;
    font-size: 9.5pt;
        clear: both;
    }

    .template { width: auto; left:0; top:0; }
    img { width:100%; }
    li { margin: 0 0 10px 20px !important;}
    }
    table {

    border-collapse: collapse;
    }

    table, th, td {
        text-align:center;
        vertical-align:middle;
        border: 1px solid black;
    }
    .wrap-word {
       word-wrap: break-word;
    }
    .margin-200 {
        margin-left:200px;
        margin-right:200px;
        margin-top:20px
    }
    th,td.padding-5 {
        padding-left:15px;
        padding-right:15px;
    }
    .text-font {
        font-weight: bold;
    }
    </style>
</head>
<body class="margin-200">
    <div class="text-font ">
        Month: ${month}    Year: ${year}
        <br>
        <br>
        Division: IT
        <br>
        <br>
        Name: <sec:loggedInUserInfo field='username'/>
    </div>
    <table class="">
        <thead>
            <tr >
                <th>Date</th>
                <th class="padding-5">Day</th>
                <th class="padding-5">StartTime</th>
                <th class="padding-5">EndTime</th>
                <th class="wrap-word">Actual Working Hours</th>
                <th class="padding-5">Hours Of Overtime (For Working Day)</th>
                <th class="padding-5">Hours Of Overtime (For Sunday)</th>
            </tr>
        </thead>
        <tbody>
            <g:each var="temp" in="${data}" status="stt">
                <tr>
                    <td class="padding-5">${temp.day}</td>
                    <td class="padding-5"><g:message code="day.of.week.${temp.weekday}" /></td>
                    <td class="padding-5">${temp.startTime}</td>
                    <td class="padding-5">${temp.endTime}</td>
                    <td class="padding-5">${temp.actualTime}</td>
                    <td class="padding-5">${temp.normalOvertime}</td>
                    <td class="padding-5">${temp.weekendOvertime}</td>
                </tr>
            </g:each>
            <tr>

                <th colspan="5">Total</th>
                <th>${totalNormalOverTime}</th>
                <th>${totalWeekendOverTime}</th>
            </tr>
        </tbody>
    </table>
    <br>
    <div class="text-font " style="float:right;padding-right:50px">
        Approval:
        <br>
        <br>
        <br>
        <br>
        <br>
    </div>
    <br>
    <div class="text-font " style="float:right;margin-right:50px">
        ___________

    </div>
</body>
</html>
