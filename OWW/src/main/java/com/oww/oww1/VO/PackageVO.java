package com.oww.oww1.VO;

/**
 * 패키지 VO
 * - 필드명은 스네이크 표기(package_no 등)를 유지한다.
 * - 템플릿/코드에서 카멜 표기(packageNo 등)를 사용할 수 있도록
 *   브릿지용 게터/세터를 함께 제공한다.
 * - 모든 숫자형은 원시형 int 사용.
 */
public class PackageVO {

    /* 실제 보관 필드(스네이크 표기) */
    private int package_no;
    private int type;
    private int hall;
    private int studio;
    private int dress;
    private int makeup;
    private int discount;

    /* 기본 생성자 */
    public PackageVO() {}

    /* ---------- 스네이크 표기용 게터/세터 (기존 코드 호환) ---------- */

    public int getPackage_no() { return package_no; }
    public void setPackage_no(int package_no) { this.package_no = package_no; }

    public int getType() { return type; }
    public void setType(int type) { this.type = type; }

    public int getHall() { return hall; }
    public void setHall(int hall) { this.hall = hall; }

    public int getStudio() { return studio; }
    public void setStudio(int studio) { this.studio = studio; }

    public int getDress() { return dress; }
    public void setDress(int dress) { this.dress = dress; }

    public int getMakeup() { return makeup; }
    public void setMakeup(int makeup) { this.makeup = makeup; }

    public int getDiscount() { return discount; }
    public void setDiscount(int discount) { this.discount = discount; }

    /* ---------- 카멜 표기 브릿지 게터/세터 (Thymeleaf/일부 코드 호환) ---------- */

    /** packageNo ←→ package_no 브릿지 */
    public int getPackageNo() { return package_no; }
    public void setPackageNo(int packageNo) { this.package_no = packageNo; }

    /** hall 그대로지만, 혹시 카멜 표기로 참조하는 경우를 대비 */
    public int getHallId() { return hall; }       // 사용 안 하면 무시 가능
    public void setHallId(int hallId) { this.hall = hallId; }

    /** studio 그대로지만, 카멜 표기 대비 */
    public int getStudioId() { return studio; }
    public void setStudioId(int studioId) { this.studio = studioId; }

    /** dress 그대로지만, 카멜 표기 대비 */
    public int getDressId() { return dress; }
    public void setDressId(int dressId) { this.dress = dressId; }

    /** makeup 그대로지만, 카멜 표기 대비 */
    public int getMakeupId() { return makeup; }
    public void setMakeupId(int makeupId) { this.makeup = makeupId; }
}
