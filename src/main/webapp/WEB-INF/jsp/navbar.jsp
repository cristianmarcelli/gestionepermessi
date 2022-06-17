<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<header>
  <!-- Fixed navbar -->
 <nav class="navbar navbar-expand-lg navbar-dark bg-primary" aria-label="Eighth navbar example">
    <div class="container">
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarsExample07" aria-controls="navbarsExample07" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse" id="navbarsExample07">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0 ">
          <li class="nav-item">
            <a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}/home">Home</a>
          </li>

           <sec:authorize access="hasRole('ADMIN')">
           
           
           <li class="nav-item dropdown">
		        <a class="nav-link dropdown-toggle" href="#" id="dropdown01" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Utenti</a>
		        <div class="dropdown-menu" aria-labelledby="dropdown01">
		          <a class="dropdown-item" href="${pageContext.request.contextPath}/utente/search">Ricerca Utenti</a>
		          <a class="dropdown-item" href="${pageContext.request.contextPath}/utente/insert">Inserisci Utente</a>
		        </div>
		      </li>
           
           	            <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" id="dropdown07" data-bs-toggle="dropdown" aria-expanded="false">Dipendenti</a>
            <ul class="dropdown-menu" aria-labelledby="dropdown07">
            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/dipendente/list">Lista Dipendenti</a></li>
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/dipendente/search">Ricerca Dipendenti</a></li>
            </ul> 
          </li>
           
		   </sec:authorize>
        </ul>
      </div>
      <sec:authorize access="isAuthenticated()">
      <div class="col-md-3 text-end">
        <p class="navbar-text">Utente: ${userInfo.username } (${userInfo.getDipendenteDTO().nome } ${userInfo.getDipendenteDTO().cognome })
      </div>
      </sec:authorize>
     
      <div class="collapse navbar-collapse text-right" id="navbarsExample07">
      	<ul class="navbar-nav me-auto mb-2 mb-lg-0">
      		   <sec:authorize access="hasAnyRole('ADMIN', 'BO_USER')">
			   <li class="nav-item dropdown ">
		        <a class="nav-link dropdown-toggle " href="#" id="dropdown01" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Gestione Account</a>
		        <div class="dropdown-menu " aria-labelledby="dropdown01">
		          <a class="dropdown-item" href="${pageContext.request.contextPath}/logout">Logout</a>
		          <a class="dropdown-item"  href="${pageContext.request.contextPath}/reset">Reset Password</a>
		        </div>
      </sec:authorize>
      </ul>
    </div>
    
    </div>
  </nav>
  
  
</header>
