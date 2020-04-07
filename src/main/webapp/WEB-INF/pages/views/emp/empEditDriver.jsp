<%@ include file="../../template/header.jsp"%>
<form:form method="post" modelAttribute="driver"  action="/employee/driver/update=${driverId}">
    <form:hidden path="id"/><br/>
    <form:hidden path="personal_number"/><br/>
    <div>
        <label for="first_name">First name</label></br>
        <form:input path="first_name" id="first_name" required="true"/>
    </div>
    <div>
        <label for="second_name">Last name</label></br>
        <form:input path="second_name" id="second_name" required="true"/>
    </div>
    <div>
        <label for="status">Status</label></br>
        <form:hidden path="status"/>
    </div>
    <%--<div>
        <label for="status">Status</label></br>
        <form:select path="status" id="status" required="true">
            <form:option value=""/>
            <form:option value="REST"/>
            <form:option value="REST_IN_SHIFT"/>
            <form:option value="DRIVING"/>
        </form:select>
    </div>--%>
    <div>
        <label for="city_id">Current city</label></br>
        <select id="city_id" name="city_id" required="true">
            <option value=""/>
            <c:forEach items="${cities}" var="cityOption">
                <option value="${cityOption.id}">${cityOption.name}</option>
            </c:forEach>
        </select>
    </div>
    <%--<div>
        <label for="wagon_id">Current wagon</label></br>
        <select id="wagon_id" name="wagon_id">
            <option value=""/>
            <c:forEach items="${wagons}" var="wagonOption">
                <option value="${wagonOption.id}">${wagonOption.id}</option>
            </c:forEach>
        </select>
    </div>--%>
    <input type="submit" value="Update driver"/>
</form:form>
<%@ include file="../../template/footer.jsp"%>