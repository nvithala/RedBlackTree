

/**
 * Created by nihar on 3/17/2016.
 */
public class RedBlackTree {

    private static boolean RED = false;
    private  static boolean BLACK = true;
    private TreeNode nil = new TreeNode();
    private  TreeNode root = nil;


                            //****** CONSTRUCT TREE FROM ARRAY ***********

    public TreeNode buildTree(int nums[][])
    {
        System.out.println("Tree Building started");
        if (nums.length == 0)
            return null;
        int height = (int) Math.ceil((Math.log(nums.length))/Math.log(2));
        int level = 0;
        root = buildTree(nums, 0, nums.length - 1,level,height);
        root.parent = nil;
        return root;
    }
    public TreeNode buildTree(int nums[][],int start,int end,int level,int height)
    {
        if (start > end)
            return nil;
        int mid = (start + end) / 2;
        TreeNode node = new TreeNode(nums[mid][0],nums[mid][1]);
        if(level == 0)
            node.setColour(BLACK);
        if(level == height-1)
            node.setColour(RED);
        node.left = buildTree(nums, start, mid - 1,level+1,height);
        node.left.parent = node;
        node.right = buildTree(nums, mid+1, end,level+1,height);
        node.right.parent = node;

        return node;
    }

    /*
    void preOrder(TreeNode node) {
        if ( node==nil || node ==null) {
            return;
        }
        System.out.print(node.val);
        if(node.left !=null)
        preOrder(node.left);
        if(node.right !=null)
        preOrder(node.right);
    }
    */

    /*
                                  ********** RB_TREE METHODS **********
     */
    public void insertNode(int id,int count) {
        TreeNode node = new TreeNode(id,count);
        node.setColour(RED);
        node.left = nil;
        node.right = nil;
        if (root == nil) {
            root = node;
            root.setColour(BLACK);
            root.parent = nil;
            return;
        }
        TreeNode thisNode = root;
        TreeNode trail = root;
        while (thisNode != nil) {
            trail = thisNode;
            if (node.val < thisNode.val)
                thisNode = thisNode.left;
            else
                thisNode = thisNode.right;
        }
        node.parent = trail;
        if (trail == nil)
            root = node;
        if (node.val < trail.val)
            trail.left = node;
        else
            trail.right = node;

        RB_tree_fixup(node);

    }

    public void RB_tree_fixup(TreeNode node)
    {

        while(node.parent.getColour() == RED && node!=root)
        {
            if(node.parent == node.parent.parent.left &&node.parent!=nil)
            {
                //y is the uncle of node
                TreeNode y = node.parent.parent.right;
                if(y.getColour() == RED && y!=nil)
                {
                    node.parent.setColour(BLACK);
                    y.setColour(BLACK);
                    node.parent.parent.setColour(RED);
                    node = node.parent.parent;

                }
                //convert case 2 to case 3 by rotation
                else
                {
                    if(node == node.parent.right)
                    {
                        node = node.parent;
                        rotateLeft(node);
                    }
                    //case 3
                    node.parent.setColour(BLACK);
                    node.parent.parent.setColour(RED);
                    rotateRight(node.parent.parent);
                }

            }
            else
            {
                TreeNode y = node.parent.parent.left;
                if(y.getColour() == RED && y!=nil)
                {
                    node.parent.setColour(BLACK);
                    y.setColour(BLACK);
                    node.parent.parent.setColour(RED);
                    node = node.parent.parent;
                }
                else{
                    if(node == node.parent.left)
                    {
                        node = node.parent;
                        rotateRight(node);
                    }
                    node.parent.setColour(BLACK);
                    node.parent.parent.setColour(RED);
                    rotateLeft(node.parent.parent);
                }

            }

        }
        root.setColour(BLACK);
    }
                            //******** METHODS FOR ROTATING LEFT AND RIGHT **********

    public void rotateRight(TreeNode y)
    {
        TreeNode x = y.left;
        y.left = x.right;
        //change colours
        if(x.right != nil)
            x.right.parent = y;
        x.parent = y.parent;
        if(y.parent == nil)
            root = x;
        else if(y == y.parent.left)
            y.parent.left = x;
        else
            y.parent.right = x;
        x.right = y;
        y.parent = x;
    }


    public void rotateLeft(TreeNode x)
    {
        TreeNode y = x.right;
        x.right = y.left;
        if(y.left != nil)
            y.left.parent = x;
        y.parent = x.parent;
        if(x.parent == nil)
            root = y;
        else if(x == x.parent.left)
            x.parent.left = y;
        else
            x.parent.right = y;
        y.left = x;
        x.parent = y;
    }
    public void RB_Transplant(TreeNode x, TreeNode y)
    {
        if(x.parent == nil)
            root = y;
        else if(x == x.parent.left)
            x.parent.left = y;
        else
            x.parent.right = y;
        y.parent = x.parent;
    }

    /*
                                      ***********   COMMAND METHODS***********
     */


    public void deleteNode(TreeNode z)
    {
        TreeNode y = z;
        TreeNode x;
        boolean y_orig_colour = z.getColour();
        if(z.left == nil ||z.left==null) {
            x = z.right;
            RB_Transplant(z, z.right);
        }
        else if(z.right == nil || z.right==null)
        {
            x = z.left;
            RB_Transplant(z,z.right);

        }
        else
        {
            y = TREE_MINIMUM(z.right);
            y_orig_colour = y.getColour();
            x = y.right;
            if(y.parent == z)
                x.parent = y;
            else
            {
                RB_Transplant(y,y.right);
                y.right = z.right;
                y.right.parent = y;
            }
            RB_Transplant(z,y);
            y.left = z.left;
            y.left.parent = y;
            y.setColour(z.getColour());
        }
        if(y_orig_colour == BLACK)
            RB_delete_fixup(x);

    }
    public void RB_delete_fixup(TreeNode x)
    {
        TreeNode w;
        while(x!=root && x.getColour()==BLACK )
        {
            if(x == x.parent.left) {
                w = x.parent.right;
                if (w.getColour() == RED) {
                    w.setColour(BLACK);
                    x.parent.setColour(RED);
                    rotateLeft(x.parent);
                    w = x.parent.right;
                }
                if (w.left.getColour() == BLACK && w.right.getColour() == BLACK) {
                    w.setColour(RED);
                    x = x.parent;
                } else if (w.right.getColour() == BLACK) {
                    w.left.setColour(BLACK);
                    w.setColour(RED);
                    rotateRight(w);
                    w = x.parent.right;
                }
                w.setColour(x.parent.getColour());
                x.parent.setColour(BLACK);
                w.right.setColour(BLACK);
                rotateRight(x.parent);
                x = root;
            }
            else
            {
                w = x.parent.left;
                if(w.getColour() == RED)
                {
                    w.setColour(BLACK);
                    x.parent.setColour(RED);
                    rotateRight(w);
                    w = x.parent.right;

                }
                if (w.right.getColour() == BLACK && w.left.getColour() == BLACK) {
                    w.setColour(RED);
                    x = x.parent;
                } else if (w.left.getColour() == BLACK) {
                    w.right.setColour(BLACK);
                    w.setColour(RED);
                    rotateLeft(w);
                    w = x.parent.left;
                }
                w.setColour(x.parent.getColour());
                x.parent.setColour(BLACK);
                w.left.setColour(BLACK);
                rotateLeft(x.parent);
                x = root;
            }
            x.setColour(BLACK);
        }
    }

    public TreeNode TREE_MINIMUM(TreeNode x)
    {
        while(x!=null && x.left !=nil && x.left!=null)
            x = x.left;

        return x;

    }

    //code to increase the count,need to check if we have to use getter and setter methods or use counts

    /*
                                ************  INCREASE QUERY ****************
     */
    public void increase(int ID,int m)
    {
        TreeNode x =searchID(ID);
        if(x == nil) {
            //if id is not present insert the node and print its m value
            insertNode(ID,m);
            //preOrder(root);
            System.out.println(m);
        }
        else
        {
            int curr_count = x.count;
            curr_count += m;
            x.count = curr_count;
            System.out.println(x.count);
        }

    }

    /*
                                ************  COUNTING QUERY ****************
     */


    public void count(int ID)
    {
        TreeNode x = searchID(ID);
        if(x != nil)
        {
            int curr_count = x.count;
            if(curr_count != 0)
                System.out.println(curr_count);
            else
                System.out.println("0");
        }
        //else
            //System.out.println("Error! node not found");

    }


     //   ************  REDUCE QUERY ****************

    public void reduce(int ID,int m)
    {
        TreeNode x = searchID(ID);
        if(x!=nil)
        {
            int curr_count = x.count;
            curr_count = curr_count - m;
            if(curr_count > 0)
            {
                x.count = curr_count;
                System.out.println(curr_count);
            }
            else
            {
                deleteNode(x);
               //count is 0 for this node as the node has to be deleted
                System.out.println("0");
            }
        }
        else
          System.out.println("0");

    }

     //                           ************  INRANGE QUERY ****************


    public void inrange(int ID1,int ID2)
    {
        System.out.println(inRangeHelper(root, ID1, ID2));
    }
    public int inRangeHelper(TreeNode root,int ID1,int ID2)
    {
        //System.out.println("In range helper entered");
        if(root==nil || root==null)
            return 0;
        if(root.val == ID1 && root.val==ID2)
            return root.count;

        //ID1 and ID2 lie on other side of the root
        if(ID1<=root.val  &&ID2>=root.val){
            return root.count+inRangeHelper(root.left,ID1,ID2)+inRangeHelper(root.right,ID1,ID2);
        }
        //ID1 lies on left side
        else if(ID1 < root.val)
        {
            return inRangeHelper(root.left,ID1,ID2);
        }
        //ID2 lies on right side of root
        else {
            return inRangeHelper(root.right, ID1, ID2);
        }
    }


     //      PREVIOUS


    public void previous(int ID)
    {
        TreeNode k = prevHelper(ID,root,null);
        if(k == null)
            System.out.println("0 0");
        else
            System.out.println(k.val +"\t"+k.count);
    }


    public TreeNode prevHelper(int ID,TreeNode root,TreeNode res)
    {
        TreeNode temp = root;
        if(temp == nil || temp==null)
        {
            return res;
        }
        if(ID == temp.val) {
            if (temp.left != nil && temp.left!=null) {
                TreeNode buff = temp.left;
                while (buff.right != nil && buff.right!=null) {
                    buff = buff.right;
                }
                res = buff;
            }

           // System.out.println("before return");
            return res;
        }
        else if( ID < temp.val)
        {
           res = prevHelper(ID,temp.left,res);
        }
        else
        {
            res = temp;
            return prevHelper(ID,temp.right,res);
        }
        //System.out.println("last return");
        return res;
    }


   //     NEXT


    public void next(int ID)
    {

        TreeNode k =nextHelper(ID,root,null);
        //System.out.println(k);
        if(k == null)
            System.out.println("0 0");
        else
            System.out.println(k.val +" "+k.count);
    }
    public TreeNode nextHelper(int ID,TreeNode x,TreeNode res)
    {

        TreeNode temp = x;
        if(temp == nil || temp==null)
        {
            return res;
        }

        if(ID == temp.val) {
            if (temp.right != nil) {
                TreeNode buff = temp.right;
                while (buff.left != nil) {
                    buff = buff.left;
                }
                res = buff;
            }
            //System.out.println("before return");
            return res;
        }
        else if( ID < temp.val)
        {
            res = temp;
            return nextHelper(ID,temp.left,res);
        }
        else
        {
            res = nextHelper(ID,temp.right,res);
        }

        return res;
    }

    public TreeNode leftMostChild(TreeNode n)
    {
        if (n==nil)
            return null;
        while(n.left!=nil)
        {
            n = n.left;
        }
        return n;
    }

    //method to search the given ID within the red black tree
    public TreeNode searchID(int ID)
    {
        TreeNode index = root;
        while(index !=nil && index!=null)
        {
            //System.out.println(index.val);
            if(ID == index.val)
                return index;
            else if(ID < index.val)
                index = index.left;
            else
                index = index.right;

        }
        return nil;
    }


}
