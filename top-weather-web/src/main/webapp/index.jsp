<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title></title>
    <meta charset="UTF-8"/>
    <meta http-equiv="X-UA-COMPATIBLE" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, init-scale=1"/>
    <link href="<c:url value='/resources/css/bootstrap.css'/>" type="text/css"/>
    <link href="<c:url value='/resources/css/bootstrap-theme.min.css'/>" type="text/css"/>
    <link href="<c:url value='/resources/css/top-weather.css'/>" type="text/css"/>
    <link href="<c:url value='/resources/css/weather-icons.min.css'/>" type="text/css"/>

</head>
<body class="wrapper">
    <nav id="nav" class="navbar navbar-default navbar-fixed-top" role="navigation">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar-collapse-main">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Title</a>
        </div>
        <div class="dropdown-menu">
            <div class="dropdown">
                <ul class="nav navbar-nav navbar-right">
                    <li><a class="menu-item" href="#main">Main</a></li>
                    <li><a class="menu-item" href="#about">About</a></li>
                    <li><a id="truthfulTableBtn" href="#content">All sites</a></li>
                </ul>
                <!-- /Menu -->
                <div class="dropdown">
                    <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenuBtn" data-toggle="dropdown" aria-expanded="true">
                        Выберите город
                        <span class="caret"></span>
                    </button>
                    <ul id="cityDropdown" class="dropdown-menu" role="menu" aria-labelledby="dropdownMenuBtn">
                        <li class="city" role="presentation"><a role="menuitem" class="menu-item" tabindex="-1" href="#">Могилев</a></li>
                        <li class="city" role="presentation"><a role="menuitem" class="menu-item" tabindex="-1" href="#">Москва</a></li>
                        <li class="city" role="presentation"><a role="menuitem" class="menu-item" tabindex="-1" href="#">Минск</a></li>
                        <li class="city" role="presentation"><a role="menuitem" class="menu-item" tabindex="-1" href="#">Париж</a></li>
                        <%--itterate through cities list--%>
                        <%--<c:forEach items="${locations}" var="location">--%>
                            <%--<li class="city" role="presentation"><a role="menuitem" class="menu-item" tabindex="-1" data-location="${location.uid}" href="hello.html?locationUid=${location.uid}">${location.name}</a></li>--%>
                        <%--</c:forEach>--%>
                    </ul>
                </div>
            </div>
        </div>
    </nav>

    <div id="content" class="">

    </div>

    <div id="about">

    </div>

    <footer>

    </footer>

    <div>
        <i class=""></i>
    </div>

    <script type="application/javascript" src="<c:url value='/resources/js/jquery-1.11.2.js'/>"></script>
    <script type="application/javascript" src="<c:url value='/resources/js/jquery.cookie.js'/>"></script>
    <script type="application/javascript" src="<c:url value='/resources/js/bootstrap.min.js'/>"></script>
    <script type="application/javascript" src="<c:url value='/resources/js/top-weather.js'/>"></script>

</body>
</html>
