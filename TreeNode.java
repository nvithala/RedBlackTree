/**
 * Created by nihar on 3/16/2016.
 */
public class TreeNode {
TreeNode left,right,parent;
    int val,count;
    private boolean colour;
    //for now cosidering only id

    TreeNode()
    {
        this.left = null;
        this.right = null;
        this.parent = null;
        this.colour = false;

    }
    TreeNode(int ID,int count)
    {
        val = ID;
        this.count = count;
    }
    public void setColour(boolean colour){

        this.colour=colour;
    }
    public boolean getColour()
    {
        return colour;
    }

    public TreeNode getParent(){
        return parent;
    }
    public void setParent(TreeNode parent){
        this.parent=parent;
    }



}
