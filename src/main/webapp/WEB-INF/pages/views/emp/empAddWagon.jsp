<%@ include file="../../template/header.jsp"%>
<form:form method="post" action="/employee/wagon/add" modelAttribute="wagon">
    <form:hidden path="id"></form:hidden>
    <div>
        <label for="car_plate">Car plate</label></br>
        <form:input path="car_plate" id="car_plate" type="text" required="true"/>
    </div>
    <div>
        <label for="driver_shift_count">Driver shift count</label></br>
            <form:input path="driver_shift_count" type="number" min = "0" max = "8" />
    </div>
    <div>
        <label for="capacity">Capacity</label></br>
        <form:input path="capacity" id="capacity" type="nubmer" min="0" required="true"/>
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
        <label for="current_city">Current city</label></br>
        <select name="current_city" id="current_city" required="true">
            <option value=""></option>
            <c:forEach items="${cities}" var="cityOption">
                <option value="${cityOption.id}">${cityOption.name}</option>
            </c:forEach>
        </select>
    </div>
    <div>
        <input type="submit" value="Add new wagon">
    </div>
</form:form>
<%@ include file="../../template/footer.jsp"%>
