package c

class Solution {
    fun mergeTwoLists(list1: ListNode?, list2: ListNode?): ListNode? {
        var node1: ListNode? = list1 ?: return list2
        var node2: ListNode? = list2 ?: return list1

        var resultHead: ListNode? = null
        var resultTail: ListNode? = null

        while (node1 != null || node2 != null) {
            val minNode: ListNode = when {
                node1 == null -> node2!!
                node2 == null -> node1
                else -> if (node1.`val` < node2.`val`) node1 else node2
            }

            when (minNode) {
                node1 -> node1 = minNode.next
                node2 -> node2 = minNode.next
            }

            if (resultHead == null) {
                resultHead = minNode
                resultTail = resultHead
            } else {
                resultTail!!.next = minNode
                resultTail = resultTail.next
            }
        }

        return resultHead
    }
}

class ListNode(var `val`: Int) {
    var next: ListNode? = null
}