import java.util.ArrayList;
import java.util.Scanner;

/* CAU 1: class NhanVien */
class NhanVien {
    private String maNV;
    private String hoTen;
    private String ngaySinh; // dd/MM/yyyy
    private double heSoLuong;

    public NhanVien() {
    }

    public NhanVien(String maNV, String hoTen, String ngaySinh, double heSoLuong) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.heSoLuong = heSoLuong;
    }

    public String getMaNV() { return maNV; }
    public void setMaNV(String maNV) { this.maNV = maNV; }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public String getNgaySinh() { return ngaySinh; }
    public void setNgaySinh(String ngaySinh) { this.ngaySinh = ngaySinh; }

    public double getHeSoLuong() { return heSoLuong; }
    public void setHeSoLuong(double heSoLuong) { this.heSoLuong = heSoLuong; }

    public void nhapThongTinNV() {
        Scanner sc = QuanLyNV.sc;
        System.out.print("Nhap ma nhan vien: ");
        maNV = sc.nextLine().trim();
        System.out.print("Nhap ho ten: ");
        hoTen = sc.nextLine().trim();
        System.out.print("Nhap ngay thang nam sinh (dd/MM/yyyy): ");
        ngaySinh = sc.nextLine().trim();
        while (true) {
            System.out.print("Nhap he so luong: ");
            try {
                heSoLuong = Double.parseDouble(sc.nextLine().trim());
                break;
            } catch (NumberFormatException e) {
                System.out.println("He so luong khong hop le, vui long nhap lai!");
            }
        }
    }

    public void xuatThongTinNV() {
        System.out.printf("Ma NV: %s | Ho ten: %s | Ngay sinh: %s | He so luong: %.2f",
                maNV, hoTen, ngaySinh, heSoLuong);
    }
}

/* CAU 2: class DanhSachNV */
class DanhSachNV {
    private ArrayList<NhanVien> ds = new ArrayList<>();
    private static final double LUONG_CO_SO = 2530000;

    // Nhap danh sach nhan vien
    public void nhapDSNV() {
        Scanner sc = QuanLyNV.sc;
        int n;
        while (true) {
            System.out.print("Nhap so luong nhan vien: ");
            try {
                n = Integer.parseInt(sc.nextLine().trim());
                if (n > 0) break;
            } catch (NumberFormatException e) {
                // nhap lai
            }
            System.out.println("So luong khong hop le, vui long nhap lai!");
        }
        for (int i = 0; i < n; i++) {
            System.out.println("--- Nhan vien thu " + (i + 1) + " ---");
            NhanVien nv = new NhanVien();
            nv.nhapThongTinNV();
            ds.add(nv);
        }
    }

    // Xuat danh sach nhan vien (kem tien luong)
    public void xuatDSNV() {
        if (ds.isEmpty()) {
            System.out.println("Danh sach rong!");
            return;
        }
        for (NhanVien nv : ds) {
            nv.xuatThongTinNV();
            System.out.printf(" | Luong: %,.0f%n", tinhLuong(nv));
        }
    }

    // Tim nhan vien theo ma
    public NhanVien timNVTheoMa(String maNV) {
        for (NhanVien nv : ds) {
            if (nv.getMaNV().equalsIgnoreCase(maNV)) {
                return nv;
            }
        }
        return null;
    }

    // luong = 2.530.000 * he so luong
    public double tinhLuong(NhanVien nv) {
        return LUONG_CO_SO * nv.getHeSoLuong();
    }

    // Sap xep tang dan theo tien luong
    public void sapXep() {
        ds.sort((a, b) -> Double.compare(tinhLuong(a), tinhLuong(b)));
    }
}

/* CAU 3: class QuanLyNV */
public class QuanLyNV {
    static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        DanhSachNV dsnv = new DanhSachNV();
        int chon = -1;

        do {
            System.out.println("\n========== MENU ==========");
            System.out.println("1. Nhap danh sach nhan vien");
            System.out.println("2. Xuat danh sach nhan vien (kem tien luong)");
            System.out.println("3. Tim nhan vien theo ma");
            System.out.println("4. Sap xep danh sach tang dan theo tien luong");
            System.out.println("0. Thoat");
            System.out.print("Chon chuc nang: ");
            try {
                chon = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                chon = -1;
            }

            switch (chon) {
                case 1:
                    dsnv.nhapDSNV();
                    break;
                case 2:
                    dsnv.xuatDSNV();
                    break;
                case 3:
                    System.out.print("Nhap ma nhan vien can tim: ");
                    String ma = sc.nextLine().trim();
                    NhanVien nv = dsnv.timNVTheoMa(ma);
                    if (nv != null) {
                        nv.xuatThongTinNV();
                        System.out.printf(" | Luong: %,.0f%n", dsnv.tinhLuong(nv));
                    } else {
                        System.out.println("Khong tim thay nhan vien co ma " + ma);
                    }
                    break;
                case 4:
                    dsnv.sapXep();
                    System.out.println("Da sap xep tang dan theo tien luong:");
                    dsnv.xuatDSNV();
                    break;
                case 0:
                    System.out.println("Thoat chuong trinh.");
                    break;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        } while (chon != 0);
    }
}
