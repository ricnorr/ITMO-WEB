<template>
  <div>
    <IndexPost v-for="post in posts" :post="post" :key="post.id"/>
  </div>
</template>

<script>
import IndexPost from "./IndexPost";
export default {
  components: {IndexPost},
  name: "Index",
  props: ["posts"]
}
</script>

<style scoped>

</style>