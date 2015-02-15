<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Top Weather</title>
    <meta charset="UTF-8"/>
    <meta http-equiv="X-UA-COMPATIBLE" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link href="<c:url value="/resources/img/favicon.png"/>" rel="icon" type="image/png" >
    <link href="<c:url value='/resources/css/bootstrap.css'/>" rel="stylesheet"/>
    <link href="<c:url value='/resources/css/bootstrap-theme.min.css'/>" rel="stylesheet"/>
    <link href="<c:url value='/resources/css/top-weather.css'/>" rel="stylesheet"/>
    <link href="<c:url value='/resources/css/weather-icons.min.css'/>" rel="stylesheet"/>

</head>
<body class="wrapper">
    <nav id="nav" class="navbar navbar-default navbar-fixed-top" role="navigation">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar-collapse-main">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">Top Weather</a>
            </div>
            <div class="collapse navbar-collapse" id="navbar-collapse-main">
                <div class="dropdown">
                    <ul class="nav navbar-nav navbar-right">
                        <li><a class="menu-item" href="#main">Главная</a></li>
                        <li><a id="truthfulTableBtn" href="#content">Все сайты</a></li>
                        <li><a class="menu-item" href="#about">О сервисе</a></li>
                    </ul>
                    <div class="dropdown pull-right" style="padding-top: 1%; padding-right: 1%;">
                        <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenuBtn" data-toggle="dropdown" aria-expanded="true">
                            Выберите город
                            <span class="caret"></span>
                        </button>
                        <ul id="cityDropdown" class="dropdown-menu" role="menu" aria-labelledby="dropdownMenuBtn">
                            <%--itterate through cities list--%>
                            <c:forEach items="${locations}" var="location">
                                <li class="city" role="presentation">
                                    <a role="menuitem" class="menu-item" tabindex="-1" href="/main.html?locationUid=${location.uid}">
                                    ${location.name}
                                    </a>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </nav>

    <div id="main" class="home">
        <div class="text-vcenter">
            <h1>Top Weather Analyzer</h1>
            <h3>This is a awesome weather analyzer</h3>
            <a href="#content" id="btn-top-10" class="btn btn-default btn-lg">Просмотр сайтов</a>
        </div>
    </div>

    <div id="content">
        <div class="container">
            <div class="row">
                <div class="col-sm-12 col-lg-12 col-lg-offset-0">
                    <table>
                        <caption></caption>
                        <thead>

                        </thead>
                        <tbody>

                        </tbody>
                    </table>
                </div>
            </div>

        </div>
    </div>

    <div id="about">
        <div></div>
    </div>

    <footer>
        <hr />
        <div class="container">
            <p class="text-right">Copyright &copy; Artezio ©</p>
        </div>
    </footer>

    <ul class="nav pull-right scroll-top">
        <li>
            <a href="#" title="Scroll to top">
                <i class="glyphicon glyphicon-chevron-up"></i>
            </a>
        </li>
    </ul>

    <script type="application/javascript" src="<c:url value='/resources/js/jquery-1.11.2.js'/>"></script>
    <script type="application/javascript" src="<c:url value='/resources/js/jquery.cookie.js'/>"></script>
    <script type="application/javascript" src="<c:url value='/resources/js/bootstrap.min.js'/>"></script>
    <script type="application/javascript" src="<c:url value='/resources/js/top-weather.js'/>"></script>

</body>
</html>
