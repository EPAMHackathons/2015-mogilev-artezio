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
                    <table id="forecastTable" class="table table-bordered">
                        <caption class="text-center caption" style="font-size: 18px; font-weight: bold; color: #000">Процент достоверности прогноза</caption>
                        <thead>
                            <tr>
                                <th rowspan="2">Информационный ресурс</th>
                                <th colspan="2">За день</th>
                                <th colspan="2">За неделю</th>
                                <th colspan="2">За месяц</th>
                            </tr>
                            <tr>
                                <th>Температура</th>
                                <th>Влажность </th>
                                <th>Температура</th>
                                <th>Влажность</th>
                                <th>Температура</th>
                                <th>Влажность</th>
                            </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${tableDto.rowDtoMap}" var="rowDto">
                            <tr id="${rowDto.key.providerId}">
                                <td>
                                    <a href="${rowDto.key.uiLink}">${rowDto.key.name}</a>
                                </td>
                                <c:forEach items="${rowDto.value.cellDtoMap}" var="cellDto">
                                    <td>
                                        <c:if test="${empty cellDto.value.rate}">
                                            -
                                        </c:if>
                                        <c:if test="${not empty cellDto.value.rate}">
                                            ${cellDto.value.rate} %
                                        </c:if>
                                    </td>
                                </c:forEach>
                            </tr>
                            <tr id="${rowDto.key.providerId}-weather" class="collapse">
                                <td colspan="7">
                                    <div class="col-sm-12 col-lg-12">
                                        <c:forEach items="${rowDto.value.requests}" var="forecastObject">
                                            <div class="col-sm-1 col-lg-1 weatherBox" style="font-size: 24px;">
                                                <div class="col-sm-12 col-lg-12">
                                                    <span>${forecastObject.forecastDayOfWeek}</span>
                                                </div>
                                                <c:forEach items="${forecastObject.forecasts}" var="forecast">
                                                    <c:if test="${forecast.key eq 'PHENOMENA_DAY'}">
                                                        <div class="col-sm-12 col-lg-12 icon-font-table-lg" style="padding-bottom: 10px; padding-top: 10px;">
                                                            <i class="wi wi-day-lightning"></i>
                                                                <%--<i class="wi ${forecast.key.icon}"></i>--%>
                                                        </div>
                                                    </c:if>
                                                    <c:if test="${forecast.key eq 'TEMPERATURE_DAY'}">
                                                        <div class="col-sm-12 col-lg-12">День</div>
                                                        <div class="col-sm-12 col-lg-12 icon-font-table-md">
                                                            <span>${forecast.value.value}<i class="wi wi-celsius"></i></span>
                                                        </div>
                                                    </c:if>
                                                    <c:if test="${forecast.key eq 'PHENOMENA_NIGHT'}">
                                                        <div class="col-sm-12 col-lg-12 icon-font-table-lg" style="padding-bottom: 10px; padding-top: 10px;">
                                                            <i class="wi wi-day-lightning"></i>
                                                                <%--<i class="wi ${forecast.key.icon}"></i>--%>
                                                        </div>
                                                    </c:if>
                                                    <c:if test="${forecast.key eq 'TEMPERATURE_NIGHT'}">
                                                        <div class="col-sm-12 col-lg-12">Ночь</div>
                                                        <div class="col-sm-12 col-lg-12 icon-font-table-md">
                                                            <span>${forecast.value.value}<i class="wi wi-celsius"></i></span>
                                                        </div>
                                                    </c:if>
                                                </c:forEach>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>

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
