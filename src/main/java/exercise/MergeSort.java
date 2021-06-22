package exercise;

public class MergeSort {

  public static void main(String[] args) {
    Node head = new Node(5);
    head.next = new Node(2);
    head.next.next = new Node(3);
    head.next.next.next = new Node(1);
    head.next.next.next.next = new Node(7);

    loop(head);

    head = mergeSort(head);

    loop(head);
  }

  public static void loop(Node head){
    while (head!=null){
      System.out.print(head.data+"->");
      head=head.next;
    }
    System.out.println();
  }

  public static Node mergeSort(Node head){
    if(head==null || head.next==null)
      return head;

    Node middle = findMiddle(head);
    System.out.println(middle.data);
    Node right = mergeSort(middle.next);
    middle.next = null;
    Node left = mergeSort(head);

    Node sort = sorted(left,right);

    return sort;
  }

  private static Node sorted(Node left, Node right){
    Node result = null;
    if(left ==null)
      return right;
    if(right == null)
      return left;

    if(left.data<right.data){
      result =left;
      result.next= sorted(left.next,right);
    }
    else {
      result =right;
      result.next= sorted(left,right.next);
    }
    return result;
  }

  private static Node findMiddle(Node head){
    if(head==null)
      return head;
    Node slw=head, fst= head.next;
    while (fst!=null){
      fst=fst.next;
      while (fst!=null){
        fst=fst.next;
        slw=slw.next;
      }
    }
    return slw;
  }

  static class Node{
    public int data;
    public Node next;
    public Node(int data){
      this.data = data;
    }

  }
}
