<%@ include file="../../template/header.jsp"%>
<form:form method="post" action="/employee/cargo/add" modelAttribute="cargo">
    <form:hidden path="id"></form:hidden>
    <div>
        <label for="description">Description</label></br>
        <form:input path="description" id="description" type="text"/>
    </div>
    <div>
        <label for="weight">Weight</label></br>
        <form:input path="weight" id="weight"/>
    </div>
    <div>
        <label for="city_from">City from</label></br>
        <select name="city_from" id="city_from">
            <option value=""></option>
            <c:forEach items="${cities}" var="cityOption">
                <option value="${cityOption.id}">${cityOption.name}</option>
            </c:forEach>
        </select>
    </div>
    <div>
        <label for="city_to">City to</label></br>
        <select name="city_to" id="city_to">
            <option value=""></option>
            <c:forEach items="${cities}" var="cityOption">
                <option value="${cityOption.id}">${cityOption.name}</option>
            </c:forEach>
        </select>
    </div>
    <div>
        <input type="submit" value="add">
    </div>
</form:form>
<%@ include file="../../template/footer.jsp"%>
