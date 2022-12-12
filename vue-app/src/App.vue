<script setup>
import Home from "./components/Home.vue"
</script>

<template>
    <header>
    <div class="wrapper">

      <nav>
        <RouterLink id=reg class=button to="/register">Register</RouterLink>
        <RouterLink id=log class=button to="/logIn">Log In</RouterLink>
        <RouterLink id=fav class=button to="/favorites">Favorites</RouterLink>
      </nav>
    </div>
  </header>

  <RouterView />
</template>
