<%@ include file="../../template/header.jsp"%>
<form:form method="post" modelAttribute="wagon"  action="/employee/wagon/update=${wagonId}">
    <form:hidden path="id"/><br/>
    <div>
        <label for="car_plate">Car plate</label></br>
        <form:input path="car_plate" id="car_plate" required="true"/>
    </div>
    <div>
        <label for="driver_shift_count">Driver shift count</label></br>
        <form:input path="driver_shift_count" id="driver_shift_count" required="true"/>
    </div>
    <div>
        <label for="capacity">Capacity</label></br>
        <form:input path="capacity" id="Capacity" required="true"/>
    </div>
    <div>
        <label for="state">State</label></br>
        <form:select path="state" id="state" required="true">
            <form:option value=""/>
            <form:option value="ENABLE"/>
            <form:option value="DISABLE"/>
        </form:select>
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
        <form:select path="current_city">
            <form:options items="${cities}"/>
        </form:select>
    </div>--%>
    <input type="submit" value="Update wagon"/>
</form:form>
<%@ include file="../../template/footer.jsp"%>