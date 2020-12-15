<template>
  <div>
    <IndexPost v-for="post in posts" :post="post" :comments="comments" :key="post.id"/>
  </div>
</template>

<script>
import IndexPost from "@/components/middle/IndexPost";
export default {
  components: {IndexPost},
  name: "Index",
  props: ["posts", "comments"]
}
</script>

<style scoped>

</style>