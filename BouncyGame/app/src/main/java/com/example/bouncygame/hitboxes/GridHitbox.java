package com.example.bouncygame.hitboxes;

public class GridHitbox implements Hitbox {

    private float left;
    private float top;
    private final float width;
    private final float height;
    private final float widthOfBlocks;
    private final float heightOfBlocks;

    private boolean[][] array;


    private final RectHitbox refRect;//this hitbox is kept in memory and its vales are changed to help with gridhitbox calculation

    public GridHitbox(float left,float top,float width,float height,int numRows,int numColumns){
        this.left = left;
        this.top = top;
        this.width = width;
        this.height = height;
        this.array = new boolean[numRows][numColumns];//all false for now.
        this.widthOfBlocks = width/getColumns();
        this.heightOfBlocks = height/getRows();
        this.refRect = new RectHitbox(0,0,widthOfBlocks,heightOfBlocks);
    }

    public boolean isActive(int row,int column){
        return array[row][column];
    }

    public void activateGridSegment(int row, int column) {
        array[row][column] = true;
    }
    public void deleteGridSegment(int row, int column) {
        array[row][column] = false;
    }
//    public RectHitbox getGridSegment(int row, int column){
//        return rectArray[row][column];
//    }


    public int xToColumn(float x){
        return (int) ((x-left)/widthOfBlocks);
    }
    public int yToRow(float y){
        return (int) ((y-top)/heightOfBlocks);
    }
    public int xToColumnBoundsChecked(float x){
        int column = xToColumn(x);
        if(column<0) column = 0;
        if(column>=getColumns()) column = getColumns()-1;
        return column;
    }
    public int yToRowBoundsChecked(float y){
        int row = yToRow(y);
        if(row<0) row = 0;
        if(row>=getRows()) row = getRows()-1;
        return row;
    }

    //returns top left part of the grid segment
    public float rowToY(int row) {
        return row*heightOfBlocks+top;
    }
    public float columnToX(int column) {
        return column*widthOfBlocks+left;
    }

    public int getRows(){
        return array.length;
    }
    public int getColumns(){
        return array[0].length;
    }


    @Override
    public boolean containsPoint(float x, float y) {
        int row = xToColumn(x);
        int column = yToRow(y);
        return array[row][column];
    }

    @Override
    public boolean intersects(Hitbox otherHitbox) {
        int startRow = yToRowBoundsChecked(otherHitbox.getTop());
        int endRow = yToRowBoundsChecked(otherHitbox.getBottom());;
        int startColumn = xToColumnBoundsChecked(otherHitbox.getLeft());
        int endColumn = xToColumnBoundsChecked(otherHitbox.getRight());


        for(int row = startRow;row<=endRow;row++){
            for(int column = startColumn;column<=endColumn;column++){
                if(!isActive(row,column)){
                    continue;
                }
                refRect.setLeft(columnToX(column));
                refRect.setTop(rowToY(row));

                if(refRect.intersects(otherHitbox)){
                    return true;
                }

            }
        }

        return false;
    }

    @Override
    public boolean rayCast(LineHitbox ray, double[] collisionCoordinates) {
        double closestDistance = Double.MAX_VALUE;
        double closestX = Float.MAX_VALUE;
        double closestY = Float.MAX_VALUE;
        int startRow = yToRowBoundsChecked(ray.getTop());
        int endRow = yToRowBoundsChecked(ray.getBottom());;
        int startColumn = xToColumnBoundsChecked(ray.getLeft());
        int endColumn = xToColumnBoundsChecked(ray.getRight());

        //test every active block
        for(int row = startRow;row<=endRow;row++){
            for(int column = startColumn;column<=endColumn;column++){
                if(!isActive(row,column)){
                    continue;
                }

                //set refRect to match the values of the rect hitbox at row,column
                refRect.setLeft(columnToX(column));
                refRect.setTop(rowToY(row));

                if(refRect.rayCast(ray,collisionCoordinates)){
                    //collisionCoordinates now has hitboxToTest's collision point if it hit, or it kept its old value if there was no collision
                    double distanceToCurrentIntersection = Math.hypot(ray.getX1()-collisionCoordinates[0],ray.getY1()-collisionCoordinates[1]);
                    if(distanceToCurrentIntersection<closestDistance){//update closest values
                        closestDistance = distanceToCurrentIntersection;
                        closestX = collisionCoordinates[0];
                        closestY = collisionCoordinates[1];
                    }
                }

            }
        }

        if(closestDistance==Double.MAX_VALUE){//there was no collision
            return false;
        }else{
            //put closest values in collisionCoordinates to be used by the caller
            collisionCoordinates[0] = closestX;
            collisionCoordinates[1] = closestY;
            return true;
        }
    }

    @Override
    public float getCenterX() {
        return left+width/2;
    }

    @Override
    public float getCenterY() {
        return top+height/2;
    }

    @Override
    public float getRectDiameterX() {
        return width;
    }

    @Override
    public float getRectDiameterY() {
        return height;
    }

    @Override
    public float getRectRadiusX() {
        return width/2;
    }

    @Override
    public float getRectRadiusY() {
        return height/2;
    }

    @Override
    public float getLeft() {
        return left;
    }

    @Override
    public float getRight() {
        return left+width;
    }

    @Override
    public float getTop() {
        return top;
    }

    @Override
    public float getBottom() {
        return top+height;
    }

    public float getWidthOfBlocks() {
        return widthOfBlocks;
    }

    public float getHeightOfBlocks() {
        return heightOfBlocks;
    }

    @Override
    public void setCenterX(float x){
        moveXBy(x-getCenterX());
    }

    @Override
    public void setCenterY(float y){
        moveYBy(y-getCenterY());
    }

    @Override
    public void setLeft(float newLeft) {
        moveXBy(newLeft- getLeft());
    }

    @Override
    public void setTop(float newTop) {
        moveYBy(newTop- getTop());
    }

    @Override
    public void setRight(float newRight) {
        moveXBy(newRight- getRight());
    }

    @Override
    public void setBottom(float newBottom) {
        moveYBy(newBottom- getBottom());
    }

    @Override
    public void moveXBy(float dX) {
        left+=dX;
    }

    @Override
    public void moveYBy(float dY) {
        top+=dY;
    }

//
//
//    private void resetIterator(float left,float top, float right, float bottom){
//        int startRow = yToRow(top);
//        int endRow = yToRow(bottom);
//        int startColumn = xToColumn(left);
//        int endColumn = xToColumn(right);
//
//        if(startRow<0) startRow = 0;
//        if(startColumn<0) startColumn = 0;
//        if(endRow>=getRows()) endRow = getRows()-1;
//        if(endColumn>=getColumns()) endColumn = getColumns()-1;
//
//        gridHitboxIterator.reset(startRow,endRow,startColumn,endColumn);
//    }
//
//
//    private class GridHitboxIterator implements Iterator<Hitbox> {
//
//        private int startRow;
//        private int endRow;
//        private int startColumn;
//        private int endColumn;
//
//        private int currentRow;
//        private int currentColumn;
//
//        private boolean hasNext;
//
//        //inputs should be in bounds
//        private void reset(int newStartRow, int newEndRow, int newStartColumn, int newEndColomn){
//            this.startRow = newStartRow;
//            this.endRow = newEndRow;
//            this.startColumn = newStartColumn;
//            this.endColumn = newEndColomn;
//
//            this.currentRow = startRow;
//            this.currentColumn = startColumn-1;
//
//            this.hasNext = false;
//        }
//
//        @Override
//        public boolean hasNext() {
//            return !(currentRow==endRow&&currentColumn==endColumn);
//        }
//
//        @Override
//        public Hitbox next() {
//            do{
//                //move over by one square
//                currentColumn++;
//                if(currentColumn>endColumn){
//                    currentColumn = startColumn;
//                    currentRow++;
//                }
//            }while(!isActive(currentRow,currentColumn));
//
//
//            if(){
//                continue;
//            }
//            refRect.setLeft(columnToX(currentColumn));
//            refRect.setTop(rowToY(currentRow));
//            return refRect;
//        }
//    }


}
