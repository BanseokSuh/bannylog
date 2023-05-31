<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
  <header>
    <HelloWorld msg="You did it!" />

    <nav>
      <RouterLink to="/">Home</RouterLink>
      <RouterLink to="/write">글 작성</RouterLink>
    </nav>
  </header>

  <RouterView />
</template>
<style></style>
