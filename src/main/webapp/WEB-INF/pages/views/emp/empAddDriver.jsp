<%@ include file="../../template/header.jsp"%>
<form:form method="post" modelAttribute="driver" action="/employee/driver/add">
    <form:hidden path="id"/>
    <div>
        <label for="first_name">First name</label></br>
        <form:input path="first_name" id="first_name" required="true"/>
    </div>
    <div>
        <label for="second_name">Last name</label></br>
        <form:input path="second_name" id="second_name" required="true"/>
    </div>
    <div>
        <label for="city_id">Current city</label></br>
        <select id="city_id" name="city_id" required="true">
            <option value=""></option>
            <c:forEach items="${cities}" var="cityOption">
                <option value="${cityOption.id}">${cityOption.name}</option>
            </c:forEach>
        </select>
    </div>
    <%--<div>
        <label for="wagon_id">Current wagon</label></br>
        <select id="wagon_id" name="wagon_id">
            <option value=""></option>
            <c:forEach items="${wagons}" var="wagonOption">
                <option value="${wagonOption.id}">${wagonOption.id}</option>
            </c:forEach>
        </select>
    </div>--%>
    <input type="submit" value="Add driver"/>
</form:form>
<%@ include file="../../template/footer.jsp"%>
