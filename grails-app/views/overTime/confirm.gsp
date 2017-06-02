<!doctype html>
<html>
<head>
    <meta name="layout" content=""/>


    <asset:link rel="icon" href="favicon.ico" type="image/x-ico"/>
    <asset:javascript src="/jquery-2.2.3.min.js"/>
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
            .print-none,.dataTables_info,.TableTools{
            display: none !important;
            }

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
        Month: ${overTimeMaster.month}    Year: ${overTimeMaster.year}
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

            <g:each var="temp" in="${overTimeMaster.overtimeHistory}" status="stt">
                <g:if test="${temp.comeTime != '0' || temp.leaveTime != '0'}">
                    <g:if test="${temp.overTimeNormal != '0' || temp.overTimeWeekend != '0'}">
                        <tr>
                            <td class="padding-5">${temp.day}</td>
                            <td class="padding-5"><g:message code="day.of.week.${temp.weekday}" /></td>
                            <td class="padding-5">${temp.comeTime}</td>
                            <td class="padding-5">${temp.leaveTime}</td>
                            <td class="padding-5">${temp.actualTime}</td>
                            <td class="padding-5">${temp.overTimeNormal}</td>
                            <td class="padding-5">${temp.overTimeWeekend}</td>
                        </tr>
                    </g:if>
                </g:if>
            </g:each>
            <tr>
                <th colspan="5">Total</th>
                <th>${overTimeMaster.totalOvertime}</th>
                <th>${overTimeMaster.totalOvertimeWeekend}</th>
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
    <div class="col-sm-offset-2 col-sm-7">
        <!--<button type="button" value="Save" id="printbtn">printoput</button>-->
        <button class="btn primary print-none" name="printbtn" id="printbtn" style="float: right; width: 60px;">In</button>
    </div>
<script>
$( document ).ready(function() {
    $('#printbtn').click(function() {
        window.print();

    });
});
</script>
</body>
</html>
