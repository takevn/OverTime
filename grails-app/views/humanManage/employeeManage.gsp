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
            Person infomation
        </h1>
    </section>

    <div>
        <table class="table table-bordered" xmlns:g="http://www.w3.org/1999/html" xmlns:g="http://www.w3.org/1999/html">
            <thead>
                <tr>
                    <th class="col-sm-1">STT</th>
                    <th class="col-sm-1">FirsrName</th>
                    <th class="col-sm-1">LastName</th>
                    <th class="col-sm-1">Date Of Birth</th>
                    <th class="col-sm-1">Graduate School</th>
                    <th class="col-sm-1">Paid leave</th>
                    <th class="col-sm-1">Profile Picture</th>
                </tr>
            </thead>
            <tbody>
                <g:each var="employee" in="${employeeList}" status="stt">
                    <tr>
                        <td class="col-sm-1">${stt+1}</td>
                        <td class="col-sm-1">${employee.firstName}</td>
                        <td class="col-sm-1">${employee.lastName}</td>
                        <td class="col-sm-1">${employee.birthDate}</td>
                        <td class="col-sm-1">${employee.graduateSchool}</td>
                        <td class="col-sm-1">${employee.remainPaidLeave}</td>
                        <td class="col-sm-1">${employee.filePathProfilePicture}</td>
                    </tr>
                </g:each>
            </tbody>

        </table>
        <input type="submit" value="update" class="btn btn-primary">
    </div>


</div>
</body>
</html>
