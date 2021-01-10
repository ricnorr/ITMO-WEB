<template>
  <div>
    <IndexPost v-for="post in posts" :post="post" :commentsCount="commentsCount" :key="post.id"/>
  </div>
</template>

<script>
import IndexPost from "./IndexPost";
export default {
  components: {IndexPost},
  name: "Index",
  props: ["posts", "commentsCount"]
}
</script>

<style scoped>

</style>